<template>
    <div class ="wrapper">
        <div style="margin: 200px auto; background-color: #fff; width:350px; height 300px; padding: 20px;
        border-radius: 10px">
            <div style="margin:20px 0; text-align:center; font-size: 24px"><b>login</b></div>
            <el-form :model="user" :rules="rules" ref="userForm">
            <el-form-item prop="username">
                <el-input size="medium" style="margin:10px 0 " prefix-icon="el-icon-user" v-model="user.username" ></el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input size="medium" style="margin:10px 0 " prefix-icon="el-icon-lock" show-password v-model ="user.password" ></el-input>
            </el-form-item>
            <el-form-item style="margin: 10px 0; text-align:right">
                   <el-button type="primary" size="small" autocomplete="off" @click="login">login</el-button>
                   <el-button type="warning" size="small" autocomplete="off" @click="$router.push('/register')">register</el-button>
             </el-form-item>

            </el-form>


        </div>
    </div>
</template>


<script>
import {setRoutes} from "@/router";
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

                    ]
               }
        }
    },

    methods:{
        login(){
            this.$refs['userForm'].validate((valid) => {
              if (valid) {
                 this.request.post("/user/login",this.user).then(res=>{
                      if(res.code==='200'){
                        localStorage.setItem("user", JSON.stringify(res.data))
                        localStorage.setItem("menus", JSON.stringify(res.data.menus))


                         setRoutes()
                        this.$message.success("login succesful")
                        if(res.data.role==='ROLE_STUDENT'){
                            this.$router.push("/home")
                        }
                        else{
                          this.$router.push("/menu")
                        }

                      }
                      else{
                       this.$message.error("username or password incorrect")
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
