<template>
    <div>
     <div style="margin:10px 0">
        <div style="padding: 10px 0">
          <el-input style="width: 200px" placeholder="something name to search" suffix-icon="el-icon-search" v-model="name"></el-input>
           <el-button class="ml-5" type="primary" @click="load">search</el-button>
           <el-button class="ml-5" type="primary" @click="reset">reset</el-button></div>
        </div>
         <el-button type="primary" @click="handlerAdd" v-if="user.role==='ROLE_ADMIN'">add<i class="el-icon-circle-plus-outline"></i></el-button>

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


         <el-table :data="tableData" boarder strip header-cell-class-name="headerBg" @selection-change="handlerSelectionChange">

                  <el-table-column type="selection" width="55"></el-table-column>
                <el-table-column prop="id" label="Id" width="80">
                       </el-table-column>
                 <el-table-column prop="name" label="Course Name" width="140">
                 </el-table-column>
                 <el-table-column prop="score" label="Score">
                 </el-table-column>
                 <el-table-column prop="times" label="Times">
                 </el-table-column>
                 <el-table-column prop="teacher" label="Lecture">
                  </el-table-column>

                 <el-table-column  label="Enable">
                     <template slot-scope="scope">
                        <el-switch v-model="scope.row.state" active-color="#13ce66" inactive-color="#ccc" @change="changeEnable(scope.row)"></el-switch>
                     </template>
                 </el-table-column>
                   <el-table-column label="operation" width="300" align="center">
                     <template slot-scope="scope">
                     <el-button type="primary" @click="selectCourse(scope.row.id)">choose course</el-button>
                     <el-button type="success"  @click="handlerEdit(scope.row)" v-if ="user.role === 'ROLE_ADMIN'">edit<i class="el-icon-edit"></i></el-button>
                       <el-popconfirm
                           class="ml-5"
                           confirm-button-text='Yes'
                           cancel-button-text='Cancel'
                           icon="el-icon-info"
                           icon-color="red"
                           title="Are you sure to delete this?"
                           @confirm="handlerDelete(scope.row.id)"
                         >

                         <el-button type="danger" slot="reference" v-if="user.role==='ROLE_ADMIN'">delete<i class="el-icon-remove-outline"></i></el-button>
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
               <el-dialog title="Course Information" :visible.sync="dialogFormVisible" width="30%" >
                          <el-form-item label="name">
                              <el-input v-model="form.name" autocomplete="off"></el-input>
                          </el-form-item>
                          <el-form-item label="score">
                              <el-input v-model="form.score" autocomplete="off"></el-input>
                          </el-form-item>
                          <el-form-item label="Times">
                              <el-input v-model="form.times" autocomplete="off"></el-input>
                          </el-form-item>
                           <el-form-item label="Lecture">
                                <el-select clearable v-model="form.teacherId" placeholder="please select">
                               <el-option v-for="item in teachers" :key="item.id" :label="item.nickname" :value="item.id"></el-option>
                               </el-select>
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
    name:"Course",
     data() {
        return{
        form:{},
         tableData:[],
          name:'',
          pageNum:1,
          pageSize:10,
          total:0,
          multipleSelection:[],
          dialogFormVisible:false,
          teachers:[],
          user:localStorage.getItem("user")? JSON.parse(localStorage.getItem("user")):{}

        }
      },
      created(){
        this.load()
      },
    methods:{
     load(){
          this.request.get("/course/page",{
            params:{
                pageNum:this.pageNum,
                pageSize:this.pageSize,
                name:this.name

            }
         // +this.pageNum+"&pageSize="+this.pageSize+"&username="+this.username)
         }).then(res =>{
             console.log(res)
             this.tableData=res.data.records
             this.total=res.data.total


         })
        this.request.get("/user/role/ROLE_TEACHER").then(res =>{

          this.teachers=res.data


        })
        },

        reset(){
            this.name="",
            this.load()
        },
        changeEnable(row){
            this.request.post("/course/update",row).then(res =>{
                if(res.code==='200'){
                    this.$message.success("operation success")

                }

            })
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
            this.request.delete("/course/"+id).then(res =>{
                if(res.code==='200'){
                    this.$message.success("delete successful")

                    this.load()
                }
                else{
                    this.$message.success("delete failed")
                }
            })
        },
        save(){
                  this.request.post("/course",this.form).then(res =>{
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
        deleteBatch(){
          let ids=this.multipleSelection.map(v =>v.id)
          this.request.post("/course/del/batch",ids).then(res =>{
            if(res){
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
        handleCourseUploadSuccess(res){
            console.log(res)
            this.load()
        },
        selectCourse(courseId){
            this.request.post('/course/studentCourse/'+courseId+"/"+this.user.id).then(res =>{
                if(res.code==='200'){
                    this.$message.success("success")
                }
                else{
                    this.$message.error("failed")
                }
            })


        }




    }
}
</script>
<style scoped>
</style>