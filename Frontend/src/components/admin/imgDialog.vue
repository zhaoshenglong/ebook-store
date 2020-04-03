<template>
  <el-dialog
    title="upload image"
    :visible.sync="dialogVisibleComputed"
    :show-close="true"
    @close="emitCloseEvent"
    width="70%"
  >
    <el-form :model="book" ref="bookForm" label-width="80px" class="bookForm">
      <el-form-item label="Pick a picture" prop="img" label-width="150px">
        <el-upload
          action="http://localhost:8080/api/amdin/img/upload"
          drag
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :on-change="handleChange"
          :file-list="fileList"
          list-type="picture"
          :auto-upload="false"
          :limit="1"
        >
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">
            Drop file here or
            <em>click to upload</em>
          </div>
          <div slot="tip" class="el-upload__tip">jpg files with a size less than 500kb</div>
        </el-upload>
        <el-button type="primary" @click="upload">upload</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>
import axios from 'axios'
export default {
  name: 'ImgDialog',
  props: {
    book: {
      type: Object,
      required: true
    },
    dialogVisible: {
      type: Boolean,
      required: true
    }
  },
  data () {
    return {
      fileList: [],
      file: {},
      dialogVisibleComputed: false
    }
  },
  methods: {
    handlePreview () {},
    handleRemove () {},
    handleChange (file, fileList) {
      this.file = new FormData()
      this.file.append('bookPicture', file.raw, file.name)
      this.file.append('chunk', '0')
      console.log(file)
    },
    upload () {
      axios
        .post('/api/admin/' + this.book.id + '/img/upload', this.file, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        })
        .then(response => {
          console.log(response)
        })
        .catch(err => {
          console.log(err)
        })
    },
    emitCloseEvent () {
      this.$emit('closeDialog')
    }
  },
  watch: {
    book: function (newBook) {
      console.log(newBook)
      this.fileList.push({
        name: newBook.name,
        url: newBook.img
      })
    },
    dialogVisible: function (data) {
      this.dialogVisibleComputed = data
    }
  }
}
</script>

<style>
</style>
