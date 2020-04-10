# Assignment 5 Caching and Searching

## Run the application

The running method has been discussed in the preview assignment except `redis` and `solr`.



For `solr`, Suppose you are at the root directory of `solr`
> bin/solr start [Push Enter for every option]
> bin/solr create -c bookstore -s 2 -rf 2

After that, a solr cloud will be running on your machine. Access http://localhost:8983/solr/#/bookstore to view the admin UI.


For `redis`, suppose you are at the root directory of `redis`.

Open `redis.conf` and find `requirepass foobar`. Uncomment this sentence and change it into `requirepass HXC19970129`.

Then run `src/redis-server redis.conf`. The terminal should be stalling.


## Implementation

### Caching

Add redis support in `DAO`. For query methods, check redis first and if not present, find in database.

### Searching

Add document every time when create a new book. Searching supports `isbn`, `name`, `author`, `author_info`, `content`.

Related APIs are as follows:
```java
    

    public Book saveBook(Book book) {
        book = bookRepository.save(book);
        ValueOperations ops = redisTemplate.opsForValue();
        ListOperations listOps = redisTemplate.opsForList();
        ops.set(book.getId(), book);
        listOps.rightPush(all_books_key, book);

        final SolrClient solrClient = getSolrClient();
        try {
            solrClient.addBean("bookstore", book);
            System.out.println("It is all right");
            solrClient.commit("bookstore");
        } catch (IOException | SolrServerException e) {
            e.printStackTrace();
        }
        return book;
    }


    public Page<Book> findLike(String pattern, Pageable pageable, boolean includeDeleteBooks){
        ListOperations listOps = redisTemplate.opsForList();
        List<Book> totalBooks = listOps.range(all_books_key, 0, -1);
        if (totalBooks.size() == 0) {
            totalBooks = bookRepository.findAll();
            listOps.rightPushAll(all_books_key, totalBooks);
        }

        final SolrClient client = getSolrClient();
        String queryString = String.format("isbn:*%s* OR name:*%s* OR author:*%s* or author_info:*%s* OR content:*%s*",
            pattern, pattern, pattern, pattern, pattern);

        final SolrQuery query = new SolrQuery(queryString);

        QueryResponse response;
        SolrDocumentList documents;
        ArrayList<Book> targetBooks = new ArrayList<>();

        try {
            response = client.query("bookstore", query);
            documents = response.getResults();
            for (SolrDocument document: documents) {
                for (Book b: totalBooks) {
                    if (b.getId().equals(document.getFirstValue("id"))) {
                        if (includeDeleteBooks || !b.isDeleted()) {
                            targetBooks.add(b);
                        }
                    }
                }
            }
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }

        int start = (int)pageable.getOffset(),
                end = start + pageable.getPageSize();
        end = Math.min(end, targetBooks.size());
        return new PageImpl<>(targetBooks.subList(start, end), pageable, targetBooks.size());
    }
```