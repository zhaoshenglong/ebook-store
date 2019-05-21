<template>
  <el-dialog :title="action" :visible.sync="dialogVisibleComputed" :show-close="showClose">
    <el-form :model="book" :rules="rules" ref="bookForm" label-width="80px" class="bookForm">
      <el-form-item label="Name" prop="name">
        <el-input v-model="book.name"></el-input>
      </el-form-item>
      <el-form-item label="Author" prop="author">
        <el-input v-model="book.author"></el-input>
      </el-form-item>
      <el-form-item label="Isbn" prop="isbn">
        <el-input v-model="book.isbn"></el-input>
      </el-form-item>
      <el-form-item label="Price" prop="price">
        <el-input type="number" v-model="book.price"></el-input>
      </el-form-item>
      <el-form-item label="Stock" prop="stock">
        <el-input type="number" v-model="book.stock"></el-input>
      </el-form-item>
      <el-form-item label="Author Information" prop="authorInfo" label-width="150px">
        <el-input type="textarea" v-model="book.authorInfo" class="block-area"></el-input>
      </el-form-item>
      <el-form-item label="Book Information" prop="contentInfo" label-width="150px">
        <el-input type="textarea" v-model="book.contentInfo" class="block-area"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="updateBook('bookForm')">{{action}}</el-button>
        <el-button @click="cancleUpdate">cancle</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>
export default {
  name: "BookForm",
  data() {
    return {
      book: {
        id: "",
        name: "",
        author: "",
        isbn: "",
        price: new Number(),
        stock: new Number(),
        authorInfo: "",
        contentInfo: ""
      },
      showClose: false,
      rules: {
        name: {
          required: true,
          message: "Please input book name",
          trigger: "blur"
        },
        author: {
          required: true,
          message: "Please input book author",
          trigger: "blur"
        },
        isbn: [
          {
            required: true,
            message: "Please input book isbn",
            trigger: "blur"
          },
          {
            min: 10,
            max: 13,
            message: "Length should be 10 or 13",
            trigger: "blur"
          }
        ],
        price: [
          {
            required: true,
            message: "Please input the price",
            trigger: "blur"
          },
          {
            type: "number",
            message: "Price must be a number"
          }
        ],
        stock: [
          {
            required: true,
            message: "Please input the stock",
            trigger: "blur"
          },
          {
            type: "number",
            message: "Price must be a number"
          }
        ]
      }
    };
  },
  props: {
    oldBook: {
      type: Object,
      default: {},
      required: false
    },
    action: {
      type: String,
      default: "Create New Book",
      required: false
    },
    dialogVisible: {
      type: Boolean,
      required: true,
      default: false
    }
  },
  computed: {
    dialogVisibleComputed() {
      return this.dialogVisible;
    }
  },
  methods: {
    updateBook(formName) {
      this.$refs[fomName].validate(valid => {
        if (valid) {
          this.$emit("updateBook", this.book);
        } else {
          this.$message({
            type: "warning",
            message: "请正确填写书记信息～",
            duration: 1000
          });
          return false;
        }
      });
    },
    cancleUpdate() {
      this.$emit("cancleBookDialog");
    }
  },
  watch: {
    oldBook: function(data) {
      console.log(data);
      if (data.id !== undefined) {
        this.book.id = data.id;
        this.book.name = data.name;
        this.book.author = data.author;
        this.book.isbn = data.isbn;
        this.book.price = data.price;
        this.book.stock = data.stock;
        this.book.contentInfo = data.contentInfo;
        this.book.authorInfo = data.contentInfo;
      } else {
        this.book.name = "";
        this.book.author = "";
        this.book.isbn = "";
        this.book.price = 0;
        this.book.stock = 0;
        this.book.authorInfo = "";
        this.book.contentInfo = "";
      }
    }
  }
};
</script>

<style>
.block-area {
  height: 100px;
}
.block-area > textarea {
  height: 100%;
}
</style>
