<template>
      <el-menu :default-openeds="['1', '3']" style="min-height:100%;overflow-x:hidden"
                  background-color="rgb(48,65,86)"
                  text-color="#fff"
                  active-text-color="#ffd04b"
                  :collapse-transition="false"
                  :collapse="isCollapse"
                  router
                  @select="handleSelect">
          <div style="height:60px;line-height:60px;text-align:center">
          <img src="../assets/logo.png" alt="" style="width:20px;position:relative;top:6px;margin-right:5px">
          <b style="color:white" v-show="logoTextShow">backend system</b>
          </div>
          <div v-for="item in menus" :key="item.id">
            <div v-if="item.path">
            <el-menu-item :index="item.path">
             <i :class="item.icon"/>
             <span slot="title">{{item.name}}</span>
             </el-menu-item>
            </div>
            <div v-else>
             <el-submenu :index="item.id +''">
              <template slot="title">
               <i :class="item.icon"/>
               <span slot="title">{{ item.name }}</span>
              </template>
              <div v-for="subItem in item.children" :key="item.Id">
              <el-menu-item :index="subItem.path">
               <i :class="subItem.icon"></i>
               <span slot="title">{{subItem.name}}</span>
              </el-menu-item>
              </div>
              </el-submenu>
              </div>



          </div>
          </el-menu>
          </template>





<script>
export default{
    name:"Aside",
    props: {
        isCollapse: Boolean,
        logoTextShow: Boolean,

      },
      data(){
       return{
        menus:localStorage.getItem("menus")? JSON.parse(localStorage.getItem("menus")):[],
        opens:localStorage.getItem("menus")? JSON.parse(localStorage.getItem("menus")).map(v=>v.id+''):[]
       }
      },
      methods:{
       handleSelect(index){

       //alert(index)
        this.$router.push(index)
        //alert(this.$router.name)
       }

      }
}
</script>
<style scoped>
</style>