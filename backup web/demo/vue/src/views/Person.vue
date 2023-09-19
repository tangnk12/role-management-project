<template>
    <el-card style="width:500px;padding:20px;border: 1px solid #ccc">
      <el-form label-width="80px" size="small">
      <el-upload
        class="avatar-uploader"
        action="http://localhost:9090/file/upload"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
       >
        <img v-if="form.avatarUrl" :src="form.avatarUrl" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>

        <el-form-item label="Username">
            <el-input v-model="form.username" :disabled="true" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Nickname">
            <el-input v-model="form.nickname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Email">
            <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Phone">
            <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Address">
            <el-input text-area v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>
         <el-form-item label="Address">
             <el-button type="primary" @click="save">Save</el-button>
         </el-form-item>
      </el-form>
    </el-card>

</template>

<script>
export default{
    name:"Person",
    data(){
        return{
            form:{},
            user: localStorage.getItem("user")? JSON.parse(localStorage.getItem("user")):{}
        }
    },
    created(){
        this.getUser().then(res =>{
            console.log(res)
            this.form = res

        })
    },
    methods:{
       async getUser(){
          return (await this.request.get("/user/username/"+this.user.username)).data
       },

       save(){
          this.request.post("/user",this.form).then(res =>{
              if(res.code==='200'){
                  this.$message.success("save successful")
                  //get parent refresh method
                 this.$emit("refreshUser")

                 //renew the user information
                 this.getUser().then(res =>{
                    res.token=JSON.parse(localStorage.getItem("user")).token,
                    localStorage.setItem("username",JSON.stringify(res))
                    })

              }
              else{
                  this.$message.success("save failed")
              }
              })
       },
       handleAvatarSuccess(res){
        this.form.avatarUrl=res
       }
    }
}
</script>
<style >
.avatar-uploader{
  text-align:center;
  padding-bottom:10px;
}
.avatar-uploader.el-upload{
    border: 1px dashed #d9d9d9;
    border-radius:6px;
    cursor:pointer;
    position:relative;
    overflow:hidden;
}
.avatar-uploader .el-upload:hover{
    boarder-color:#409Eff;
}
.avatar-uploader-icon{
 font-size:28px;
 color:#8c939d;
 width:178px;
 height:178px;
 line-height:178px;
 text-align:center;
}
.avatar{
 width:178px;
 height:178px;
 display:block;
}
</style>












