<template>
    <div class ="wrapper">
        <div style="margin: 100px auto; background-color: #fff; width:350px; height 300px; padding: 20px;
        border-radius: 10px">
            <div style="margin:20px 0; text-align:center; font-size: 24px"><b>register</b></div>
            <el-form :model="user" :rules="rules" ref="userForm">
            <el-form-item prop="username">
                <el-input placeholder="please input password" size="medium" style="margin:5px 0 " prefix-icon="el-icon-user" v-model="user.username" ></el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input placeholder="please input password" size="medium" style="margin:5px 0 " prefix-icon="el-icon-lock" show-password v-model="user.password" ></el-input>
            </el-form-item>
              <el-form-item prop="confirmPassword">
                            <el-input placeholder="please confirm password" size="medium" style="margin:5px 0 " prefix-icon="el-icon-lock" show-password v-model ="user.confirmPassword" ></el-input>
                        </el-form-item>
            <el-form-item style="margin: 10px 0; text-align:right">
                   <el-button type="primary" size="small" autocomplete="off" @click="login">Register</el-button>
                   <el-button type="warning" size="small" autocomplete="off" @click="$router.push('/login')">back to login</el-button>
             </el-form-item>

            </el-form>


        </div>
    </div>
</template>


<script>
export default{
    name:"Login",
    data(){
        return{
            user:{},
               rules: {
                    username: [
                      { required: true, message: 'Username cannot be blank', trigger: 'blur' },

                    ],
                     password: [
                    { required: true, message: 'password cannot be blank', trigger: 'blur' },

                    ],
                    confirmPassword:[
                      { required: true, message: 'comfirm password cannot be blank', trigger: 'blur' },
                    ]

               }
        }
    },

    methods:{
        login(){
            this.$refs['userForm'].validate((valid) => {
              if (valid) {
                 if(this.user.password!=this.user.confirmPassword){
                    this.$message.error("password not same")
                    return false
                 }
                 this.request.post("/user/register",this.user).then(res=>{
                              if(res.code==='200'){
                                 this.$message.success("register succesful")

                              }
                              else{
                                this.$message.error(res.msg)
                              }
                          })
              }
            });
        }
    }

}
</script>

<style>
    .wrapper{
        height: 100vh;
        background-image: linear-gradient(to bottom right,#FC466B, #3f5EFB);
        overflow:hidden;
    }
</style>
