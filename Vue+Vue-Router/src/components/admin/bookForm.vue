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
        <el-input type="number" v-model.number="book.price"></el-input>
      </el-form-item>
      <el-form-item label="Stock" prop="stock">
        <el-input type="number" v-model.number="book.stock"></el-input>
      </el-form-item>
      <el-form-item label="Author Information" prop="authorInfo" label-width="150px">
        <el-input type="textarea" v-model="book.authorInfo" class="block-area"></el-input>
      </el-form-item>
      <el-form-item label="Book Information" prop="contentInfo" label-width="150px">
        <el-input type="textarea" v-model="book.contentInfo" class="block-area"></el-input>
      </el-form-item>
      <el-form-item label="Pick a picture" prop="img" label-width="150px">
        <el-upload
          action="http://localhost:8080/api/amdin/img/upload"
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :on-change="handleChange"
          :file-list="fileList"
          list-type="picture"
          :auto-upload="false"
          :limit="1"
        >
          <el-button size="small" type="primary">Select picture</el-button>
          <div slot="tip" class="el-upload__tip">jpg files with a size less than 500kb</div>
        </el-upload>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="executeAction('bookForm')">{{action}}</el-button>
        <el-button @click="cancleAction">cancle</el-button>
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
        price: 0,
        stock: 0,
        authorInfo: "",
        contentInfo: "",
        img: ""
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
      },
      fileList: []
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
    executeAction(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          let event =
            this.action === "Create New Book" ? "createBook" : "updateBook";
          this.$emit(event, this.book);
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
    cancleAction() {
      this.$emit("cancleBookDialog");
    },
    handlePreview() {},
    handleChange(file, fileList) {
      console.log(file);
      this.book.img = new FormData();
      this.book.img.append("bookImage", file, file.name);
      this.book.img.append("chunk", 0);
    },
    handleRemove() {}
  },
  watch: {
    oldBook: function(data) {
      console.log(data);
      if (data.id !== undefined) {
        this.book = data;
        this.fileList.push({
          name: data.name,
          url: data.img
        });
      } else {
        this.book = new Object();
        this.fileList = [];
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
