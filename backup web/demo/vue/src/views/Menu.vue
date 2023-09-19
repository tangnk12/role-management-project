<template>
    <div class="ml-5">
      <div style="padding: 10px 0">

         <el-input style="width: 200px" placeholder="something name to search" suffix-icon="el-icon-search" v-model="name"></el-input>
     <!--    <el-input style="width: 200px" placeholder="something email to search" suffix-icon="el-icon-message" class="ml-5" v-model="email"></el-input>-->
     <!--   <el-input style="width: 200px" placeholder="something address to search" suffix-icon="el-icon-position" class="ml-5" v-model ="address"></el-input>-->
         <el-button class="ml-5" type="primary" @click="load">search</el-button>
         <el-button class="ml-5" type="primary" @click="reset">reset</el-button></div>
         <div style="margin:10px 0">
           <el-button type="primary" @click="handlerAdd()">add<i class="el-icon-circle-plus-outline"></i></el-button>
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

         <el-table :data="tableData" boarder strip header-cell-class-name="headerBg"
         row-key="id" default-expand-all @selection-change="handlerSelectionChange">
            <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column prop="id" label="Id" align="center"></el-table-column>
           <el-table-column prop="name" label="Name" ></el-table-column>
            <el-table-column prop="path" label="Path" ></el-table-column>
            <el-table-column prop="pagePath" label="Page Path" ></el-table-column>

            <el-table-column label="Icon" class-name="fontSize18" align="center" label-class-name="fontSize12 "">
            <template slot-scope="scope">
            <i :class="scope.row.icon" />
            </template>
            </el-table-column>
           <el-table-column prop="description" label="Description" align="center"></el-table-column>
             <el-table-column label="operation" width="280" align="center">
               <template slot-scope="scope">
                 <el-button type="primary" @click="handlerAdd(scope.row.id)" v-if="!scope.row.pid&& !scope.row.path">Add Menu<i class="el-icon-plus"></i></el-button>
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

       <el-form>

      <el-dialog title="Menu Information" :visible.sync="dialogFormVisible" width="30%" >
            <el-form-item label="name">
                <el-input v-model="form.name" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="path">
                <el-input v-model="form.path" autocomplete="off"></el-input>
            </el-form-item>
             <el-form-item label="Page Path">
                <el-input v-model="form.pagePath" autocomplete="off"></el-input>
                </el-form-item>
            <el-form-item label="icon">
               <template slot-scope="scope">
                <el-select clearable v-model="form.icon" placeholder="please select"style="width:100%">
                <el-option v-for="item in options" :key="item.name" :label="item.name" :value="item.value">
                <i :class="item.value"/>{{item.name}}
                </el-option>

                </el-select>
               </template>
            </el-form-item>
            <el-form-item label="description">
                <el-input v-model="form.description" autocomplete="off"></el-input>
            </el-form-item>


         <span slot="footer" class="dialog-footer">
           <el-button @click="dialogFormVisible = false">Cancel</el-button>
           <el-button type="primary" @click="save">Save</el-button>
         </span>
    </el-dialog>
    </el-form>
   </div>

</template>

<script>
export default{
    name:"Menu",
    data() {
        return{
         tableData:[],
            name:"",
            path:"",
            icon:"",
            description:"",
            form:{},
            dialogFormVisible:false,
            multipleSelection:[],
            total:0,
            pageNum:1,
            pageSize:10,
            headerBg:'headerBg',
            options:[]
        }
      },
      created(){
      //load data from database
        this.load()

      },


      methods:{
           load(){
                this.request.get("/menu",{
                  params:{

                      name:this.name,

                  }
               // +this.pageNum+"&pageSize="+this.pageSize+"&username="+this.username)
               }).then(res =>{
                   console.log(res)
                   this.tableData=res.data


               })
              },
              save(){
                  this.request.post("/menu",this.form).then(res =>{
                      if(res.code==='200'){
                          this.$message.success("save successful")
                          this.dialogFormVisible=false
                          this.load()
                      }
                      else{
                          this.$message.success("save failed")

                      }
                      })
              },
              edit(){
              },
              reset(){
                  this.name="",


                  this.load()
              },
              handlerAdd(pid){
                  this.dialogFormVisible=true,
                  this.form={}
                  if(pid){
                    this.form.pid=pid
                    }

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
              handlerEdit(row){
                  this.form=row
                  this.dialogFormVisible=true
                  this.request.get("/menu/icons").then(res =>{
                     this.options=res.data
                 })

              },
              handlerDelete(id){
                  this.request.delete("/menu/"+id).then(res =>{
                      if(res.data==='200'){
                          this.$message.success("delete failed")

                          this.load()
                      }
                      else{
                          this.$message.success("delete success")
                          this.load()
                      }
                      })
              },
              deleteBatch(){
                let ids=this.multipleSelection.map(v =>v.id)
                this.request.post("/menu/del/batch",ids).then(res =>{
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

              exp(){
                window.open("http://localhost:9090/menu/export")



              },
              handleExcelImportSuccess(){
               this.$message.success("import successful")
               this.load()

              },


          }
}
</script>



<style>
  .headerBg{
    background-color: wheat !important;

  }
  .fontSize18{
   font-size: 18px;
  }
  .fontSize12{
   font-size: 12px;
  }
</style>