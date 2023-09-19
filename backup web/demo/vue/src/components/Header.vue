<template>
    <div style="line-height: 60px; display: flex">
        <div style="flex: 1;">
          <span :class="collapseBtnClass" style="cursor: pointer; font-size: 18px" @click="collapse"></span>
          <el-breadcrumb separator="/" style="display: inline-block; margin-left: 10px">
            <el-breadcrumb-item :to="'/'" >Main Page</el-breadcrumb-item>
           <el-breadcrumb-item>{{$route.name}} </el-breadcrumb-item>

          </el-breadcrumb>
        </div>
        <el-dropdown style="width: 150px; cursor:pointer; text-align:right">
        <div style="display: inline-block">
        <img :src=user.avatarUrl alt="" style="width: 30px;border-radius:50%; position: relative; top: 10px; right: 5px">
            <span>{{user.nickname}}</span>
            <i class="el-icon-arrow-down" style="margin-left: 5px"></i>
        </div>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item  style="font-size:14px; padding:5px 0">
              <router-link to="/person">Personal information</router-link>
              </el-dropdown-item>
              <span @click="logout">
                <el-dropdown-item style="font-size:14px; padding:5px 0">Exit </el-dropdown-item>
              </span>


            </el-dropdown-menu>
        </el-dropdown>
    </div>

</template>

<script>
export default{
    name:"Header",
    props: {
        collapseBtnClass: String,
        collapse: Function,
        user: Object
    },
    computed:{
        currentPathName(){
            return this.$store.state.currentPathName;
        }

    },
    data(){
        return{
        }
    },
    methods:{
   //     collapse(){
   //         this.$emit("asideCollapse")
   //     },
    logout(){

       localStorage.removeItem("user")
        localStorage.removeItem("menus")
         this.$router.push("/login")

       this.$message.success("logout succesful")
     }
    }





}
</script>
<style scoped>
</style>