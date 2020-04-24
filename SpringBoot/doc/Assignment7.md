# Assignment 7 HBase

Related files are in `package com.ibook.bookstore.Log`

## Part 1, Log with HBase

Inspired by teacher's program, I implemented a `Dao` class. It supplies APIs needed to access HBase.

## Part 2
I am confused with the second requirement and I have discussed with many of my classmates. 

In this implementation, file is just a table in HBase. If the rows for a table is above 1000, then create a new table.

If the tables is more than 3, than delete old one and create a new one to hold the LOG.

