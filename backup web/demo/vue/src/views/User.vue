<template>
    <div class="ml-5">
      <div style="padding: 10px 0">

         <el-input style="width: 200px" placeholder="something name to search" suffix-icon="el-icon-search" v-model="username"></el-input>
         <el-input style="width: 200px" placeholder="something email to search" suffix-icon="el-icon-message" class="ml-5" v-model="email"></el-input>
         <el-input style="width: 200px" placeholder="something address to search" suffix-icon="el-icon-position" class="ml-5" v-model ="address"></el-input>
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
               <el-upload
                            action="http://localhost:9090/user/import" style="display: inline-block" :show-file-list="false" accept="xlsx" :on-success="handleExcelImportSuccess">
                            <el-button type="primary" class="ml-5">import<i class="el-icon-top"></i></el-button>
               </el-upload>

           <el-button type="primary" @click="exp" class="ml-5">export<i class="el-icon-bottom"></i></el-button>

         </div>

         <el-table :data="tableData" boarder strip header-cell-class-name="headerBg" @selection-change="handlerSelectionChange">
            <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column prop="id" label="Id" width="80">
                 </el-table-column>
           <el-table-column prop="username" label="Username" width="140">
           </el-table-column>


           <el-table-column prop="nickname" label="Nickname" width="120">
           </el-table-column>
            <el-table-column prop="role" label="Character">
                <template slot-scope="scope">
                    <el-tag type="primary" v-if="scope.row.role==='ROLE_ADMIN'">manager</el-tag>
                    <el-tag type="warning" v-if="scope.row.role==='ROLE_TEACHER'">lecture</el-tag>
                    <el-tag type="success" v-if="scope.row.role==='ROLE_STUDENT'">student</el-tag>
                </template>
             </el-table-column>
           <el-table-column prop="email" label="Email Address">
           </el-table-column>
           <el-table-column prop="address" label="Address">
           </el-table-column>
           <el-table-column prop="phone" label="Phone Number">
           </el-table-column>

             <el-table-column label="operation" width="500" align="center">
               <template slot-scope="scope" >
                <el-button type="primary" @click="lookCourse(scope.row.courses)" v-if="scope.row.role==='ROLE_TEACHER'">Teach course<i class="el-icon-edit"></i></el-button>
                <el-button type="warning" @click="lookStuCourse(scope.row.stuCourses)" v-if="scope.row.role==='ROLE_STUDENT'">Selected course<i class="el-icon-edit"></i></el-button>

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
             :total="total">
            </el-pagination>
         </div>
       <el-form>

      <el-dialog title="User Information" :visible.sync="dialogFormVisible" width="30%" >
            <el-form-item label="Username">
                <el-input v-model="form.username"  autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="Character">
              <el-select clearable v-model="form.role" placeholder="please select character" style="width:100%">
                <el-option v-for="item in roles" :key="item.flag" :label="item.flag" :value="item.flag">
                             <i :class="item.value"/>{{item.name}}
                </el-option>
              </el-select>
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
                <el-input v-model="form.address" autocomplete="off"></el-input>
            </el-form-item>

         <span slot="footer" class="dialog-footer">
           <el-button @click="dialogFormVisible = false">Cancel</el-button>
           <el-button type="primary" @click="save">Save</el-button>
         </span>
    </el-dialog>
          <el-dialog title="Course Information" :visible.sync="vis" width="30%" >
            <el-table :data="courses">
            <el-table-column prop="name" label="Course Name">            </el-table-column>
            <el-table-column prop="score" label="Score">            </el-table-column>

            </el-table>
            </el-dialog>


              <el-dialog title="Course Select Information" :visible.sync="stuVis" width="30%" >
                <el-table :data="stuCourses">
                <el-table-column prop="name" label="Course Name">            </el-table-column>
                <el-table-column prop="score" label="Score">            </el-table-column>

                </el-table>
              </el-dialog>

    </el-form>
   </div>

</template>

<script>
export default{
    name:"User",
    data() {
        return{
         tableData:[],
            username:"",
            email:"",
            address:"",
            form:{},
            dialogFormVisible:false,
            multipleSelection:[],
            total:0,
            pageNum:1,
            pageSize:10,
            headerBg:'headerBg',
            roles:[],
            courses:[],
            stuCourses:[],
            vis:false,
            stuVis:false
        }
      },
      created(){
      //load data from database
        this.load()

      },
      methods:{
           load(){
                this.request.get("/user/page",{
                  params:{
                      pageNum:this.pageNum,
                      pageSize:this.pageSize,
                      username:this.username,
                      email:this.email,
                      address:this.address
                  }
               // +this.pageNum+"&pageSize="+this.pageSize+"&username="+this.username)
               }).then(res =>{
                   console.log(res)
                   this.tableData=res.data.records
                   this.total=res.data.total

               })
                this.request.get("/role").then(res =>{
                      this.roles=res.data

                  })
              },
              save(){
                  this.request.post("/user",this.form).then(res =>{
                        if(res==='200'){
                              this.$message.error("save failed")

                          }
                          else{
                              this.$message.success("save success")
                              this.dialogFormVisible=false
                              this.load()
                          }
                      })
              },
              edit(){
              },
              reset(){
                  this.username="",
                  this.email="",
                  this.address="",
                  this.pageNum=1,
                  this.pageSize=2,
                  this.load()
              },
              handlerAdd(){
                  this.dialogFormVisible=true,
                  this.form={}

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
              },
              handlerDelete(id){
                  this.request.delete("/user/"+id).then(res =>{
                      if(res.code==='200'){
                          this.$message.success("delete successful")

                          this.load()
                      }
                      else{
                          this.$message.success("delete failed")
                      }
                      })
              },
              deleteBatch(){
                let ids=this.multipleSelection.map(v =>v.id)
                this.request.post("/user/del/batch",ids).then(res =>{
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
                window.open("http://localhost:9090/user/export")



              },
              handleExcelImportSuccess(){
               this.$message.success("import successful")
               this.load()

              },
              lookCourse(courses){
                this.courses=courses
                this.vis=true
               },
               lookStuCourse(stuCourses){
                   this.stuCourses=stuCourses
                   this.stuVis=true
              }

          }
}
</script>



<style>
  .headerBg{
    background-color: wheat !important;

  }
</style>