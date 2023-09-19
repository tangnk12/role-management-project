<template>
 <div>
    <div style="display:flex;height:60px;line-height:60px; border-bottom:1px solid #eee">
        <div style="width :300px; display:flex;padding-left:30px">
        <div style="width:60px">
            <img src="../../assets/logo2.png" alt="" style="width: 50px;position:relative; top :5px">
        </div>
       <div style="flex:1">Welcome</div>
        </div>
        <div style ="flex:1">
        <!-- 导航菜单-->

      <!--  <ul style="list-style:none; background-color:#3a8ee6 ;">-->
         <!--     <li class="item">-->
         <!--     <el-dropdown size="medium">-->
           <!--    <span> more menu<i class="el-icon-arrow-down el-icon--right"></i></span>-->
          <!--       <el-dropdown-menu slot="dropdown">-->
           <!--        <el-dropdown-item icon="el-icon-plus">Action 1</el-dropdown-item>-->
            <!--       <el-dropdown-item>Action 2</el-dropdown-item>-->
            <!--      <el-dropdown-item>Action 3</el-dropdown-item>-->
             <!--      <el-dropdown-item>Action 4</el-dropdown-item>-->
             <!--      <el-dropdown-item>Action 5</el-dropdown-item>-->
             <!--    </el-dropdown-menu>-->
            <!--  </el-dropdown></li>-->
         <!--     <li class="item"><a href="/login">menu 2</a></li>-->
         <!--     <li class="item"><a href="/login">menu 3</a></li>-->
        <!--      <li class="item"><a href="/login">menu 4</a></li>-->
     <!--    </ul>-->
     <el-menu :default-active="'1'" class="el-menu-demo" mode="horizontal" >
       <el-menu-item index="1">Processing Center</el-menu-item>
       <el-submenu index="2">
         <template slot="title">Workspace</template>
         <el-menu-item index="2-1">item one</el-menu-item>
         <el-menu-item index="2-2">item two</el-menu-item>
         <el-menu-item index="2-3">item three</el-menu-item>
         <el-submenu index="2-4">
           <template slot="title">item four</template>
           <el-menu-item index="2-4-1">item one</el-menu-item>
           <el-menu-item index="2-4-2">item two</el-menu-item>
           <el-menu-item index="2-4-3">item three</el-menu-item>
         </el-submenu>
       </el-submenu>
       <el-menu-item index="3" disabled>Info</el-menu-item>
       <el-menu-item index="4"><a href="https://www.ele.me" target="_blank">Orders</a></el-menu-item>
     </el-menu>

        </div>

        <div style="width:200px">
         <div v-if="!user.username" style="text-align:right; padding-right:30px">
            <el-button @click="$router.push('/login')">login</el-button>
            <el-button @click="$router.push('/register')">register</el-button>

         </div>
         <div v-else>
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

        </div>
     </div>
   <div style="width:1000px;margin:0 auto">
    <router-view/>
   </div>
 </div>

</template>
<script>
export default{
    name:"Front",
    data(){
        return{
            user:localStorage.getItem("user")? JSON.parse(localStorage.getItem("user")):{}

        }
    },
    created(){

    },
    methods:{
        logout(){

            localStorage.removeItem("user")
            localStorage.removeItem("menus")
            this.$router.push("/login")

            this.$message.success("logout succesful")
        }
    }
}
</script>
<style>
.item{
    display:inline-block;
    width:100px;
    color:#FFD700;
    text-align:center;
    cursor:pointer
}
.item a{
    color: #1E90FF;
}
.item:hover{
    background-color: #cf9236

}

</style>