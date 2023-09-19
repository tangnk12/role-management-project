<template>
    <div class="ml-5">
      <div style="padding: 10px 0">

         <el-input style="width: 200px" placeholder="something name to search" suffix-icon="el-icon-search" v-model="name"></el-input>
     <!--    <el-input style="width: 200px" placeholder="something email to search" suffix-icon="el-icon-message" class="ml-5" v-model="email"></el-input>-->
     <!--   <el-input style="width: 200px" placeholder="something address to search" suffix-icon="el-icon-position" class="ml-5" v-model ="address"></el-input>-->
         <el-button class="ml-5" type="primary" @click="load">search</el-button>
         <el-button class="ml-5" type="primary" @click="reset">reset</el-button></div>
         <div style="margin:10px 0">
           <el-button type="primary" @click="handlerAdd">add<i class="el-icon-circle-plus-outline"></i></el-button>
            <el-popconfirm
                             class="ml-5"
                             confirm-button-text='Yes'
                             cancel-button-text='Cancel'
                             icon="el-icon-info"
                             icon-color="red"
                             title="Are you sure to delete these?"
                             @confirm="deleteBatch"
                           >



           <el-button type="danger" slot="reference">delete multiple<i class="el-icon-remove-outline"></i></el-button>
           </el-popconfirm>
       <!--        <el-upload-->
                        <!--     action="http://localhost:9090/user/import" style="display: inline-block" :show-file-list="false" accept="xlsx" :on-success="handleExcelImportSuccess">-->
                         <!--    <el-button type="primary" class="ml-5">import<i class="el-icon-top"></i></el-button>-->
              <!--  </el-upload>-->

           <!-- <el-button type="primary" @click="exp" class="ml-5">export<i class="el-icon-bottom"></i></el-button>-->

         </div>

         <el-table :data="tableData" boarder strip header-cell-class-name="headerBg" @selection-change="handlerSelectionChange">
            <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column prop="id" label="Id" >
                 </el-table-column>
           <el-table-column prop="name" label="Name" >
           </el-table-column>
            <el-table-column prop="flag" label="Flag" >
            </el-table-column>
           <el-table-column prop="description" label="Description" >
           </el-table-column>

             <el-table-column label="operation">
               <template slot-scope="scope">
                 <el-button type="info" @click="selectMenu(scope.row)">Distribute menu<i class="el-icon-menu"></i></el-button>


                 <el-button type="success" @click="handlerEdit(scope.row)">edit<i class="el-icon-edit"></i></el-button>
                 <el-popconfirm
                     class="ml-5"
                     confirm-button-text='Yes'
                     cancel-button-text='Cancel'
                     icon="el-icon-info"
                     icon-color="red"
                     title="Are you sure to delete this?"
                     @confirm="handlerDelete(scope.row.id)"
                   >

                   <el-button type="danger" slot="reference">delete<i class="el-icon-remove-outline"></i></el-button>
                 </el-popconfirm>
               </template>
             </el-table-column>
         </el-table>
         <div style="padding: 10px 0">
           <el-pagination
              @size-change="handleSizeChange"
              @current-change="handlerCurrentChange"
             :current-page="pageNum"
             :page-sizes="[5, 10, 10, 20]"
             :page-size="pageSize"
             layout="total, sizes, prev, pager, next, jumper"
             >
            </el-pagination>
         </div>
       <el-form>

      <el-dialog title="Character Information" :visible.sync="dialogFormVisible" width="30%" >
            <el-form-item label="name">
                <el-input v-model="form.name" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="flag">
                <el-input v-model="form.flag" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="description">
                <el-input v-model="form.description" autocomplete="off"></el-input>
            </el-form-item>


         <span slot="footer" class="dialog-footer">
           <el-button @click="dialogFormVisible = false">Cancel</el-button>
           <el-button type="primary" @click="save">Save</el-button>
         </span>
    </el-dialog>

      <el-dialog title="Distribute menu setting" :visible.sync="menuDialogVisible" width="30%" >
             <el-tree
             :props="props"
                :data="menuData"
                show-checkbox
                node-key="id"
                ref="tree"
                :check-strictly="true"
                  :default-expanded-keys="expends"
                  :default-checked-keys="checks"
             >
                <span class="custom-tree-node" slot-scope="{ node, data }">
                   <span><i :class="data.icon"></i>{{ data.name}}</span>
                </span>
             </el-tree>


             <span slot="footer" class="dialog-footer">
               <el-button @click="menuDialogVisible = false">Cancel</el-button>
               <el-button type="primary" @click="saveRoleMenu(roleId)">Save</el-button>
             </span>
        </el-dialog>
    </el-form>
   </div>

</template>

<script>
export default{
    name:"Role",
    data() {
        return{
         tableData:[],
            name:"",
            email:"",
            address:"",
            form:{},
            dialogFormVisible:false,
            menuDialogVisible:false,
            multipleSelection:[],

            total:0,
            pageNum:1,
            pageSize:10,
            headerBg:'headerBg',
            menuData:[],
            props:{
                label:'name',
            },
            expends:[],
            checks:[],
            roleId:0,
            roleFlag:''

        }
      },
      created(){
      //load data from database
        this.load()

      },
      methods:{
           load(){
                this.request.get("/role/page",{
                  params:{
                      pageNum:this.pageNum,
                      pageSize:this.pageSize,
                      name:this.name,

                  }

               }).then(res =>{
                   console.log(res)
                   this.tableData=res.data.records
                   this.total=res.data.records

               })


              },
              save(){
                  this.request.post("/role",this.form).then(res =>{
                      if(res.code==='200'){
                          this.$message.success("save successful")
                          this.dialogFormVisible=false


                      }
                      else{
                          this.$message.success("save failed")
                      }
                      })

                       this.load()


              },
                saveRoleMenu(rowId){
                     this.request.post("/role/roleMenu/"+this.roleId,this.$refs.tree.getCheckedKeys()).then(res =>{
                          if(res.code==='200'){
                            this.$message.success("wrap success")
                            this.menuDialogVisible=false;
                          }
                          else{
                            this.$message.error("wrap failed")
                          }
                            localStorage.removeItem("user")
                            localStorage.removeItem("menus")
                            this.$router.push("/login")



                      })
                },
              edit(){
              },
              reset(){
                  this.name="",

                  this.pageNum=1,
                  this.pageSize=2,
                  this.load()
              },


              handleSizeChange(pageSize){
                  console.log(pageSize)
                  this.pageSize=pageSize
                  this.load()
              },
              handlerCurrentChange(pageNum){
                  console.log(pageNum)
                  this.pageNum=pageNum
                  this.load()

              },
                handlerAdd(){
                    this.dialogFormVisible=true,
                    this.form={}

                },
              handlerEdit(row){
                  this.form=row
                  this.dialogFormVisible=true
              },
              handlerDelete(id){
                  this.request.delete("/role/"+id).then(res =>{
                      if(res.data==='200'){
                          this.$message.failed("delete failed")


                      }
                      else{
                          this.$message.error("delete success")
                          this.load()
                      }
                      })

              },
              deleteBatch(){
                let ids=this.multipleSelection.map(v =>v.id)
                this.request.post("/row/del/batch",ids).then(res =>{
                  if(res.code==='200'){
                      this.$message.success("delete multiple successful")
                      this.load()
                  }
                  else{
                      this.$message.success("delete failed")
                  }
                  })

              },
              handlerSelectionChange(val){
                console.log(val)
                this.multipleSelection=val
              },
            selectMenu(role){

                this.menuDialogVisible=true
                this.roleId=role.id
                this.roleFlag=role.flag

                    this.request.get("/menu").then(res =>{
                      this.menuData=res.data
                      //return backend data and transfer to id list
                      this.expends=this.menuData.map(v=>v.id)
                  })
                  this.request.get("/role/roleMenu/"+ this.roleId).then(res =>{
                    this.checks=res.data


                  })
            },
           handleCheckChange(data, checked, indeterminate) {
                console.log(data, checked, indeterminate);
           },




          }
}
</script>



<style>
  .headerBg{
    background-color: wheat !important;

  }
</style>