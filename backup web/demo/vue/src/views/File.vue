<template>
    <div>
     <div style="margin:10px 0">
        <div style="padding: 10px 0">
          <el-input style="width: 200px" placeholder="something name to search" suffix-icon="el-icon-search" v-model="name"></el-input>
           <el-button class="ml-5" type="primary" @click="load">search</el-button>
           <el-button class="ml-5" type="primary" @click="reset">reset</el-button></div>
        </div>
        <el-upload
             action="http://localhost:9090/file/upload" style="display: inline-block" :show-file-list="false" accept="xlsx" :on-success="handleFileUploadSuccess">
             <el-button type="primary" class="ml-5">Upload file<i class="el-icon-top"></i></el-button>
        </el-upload>
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
                 <el-table-column prop="name" label="FileName" width="140">
                 </el-table-column>
                 <el-table-column prop="type" label="FileType">
                 </el-table-column>
                 <el-table-column prop="size" label="FileSize">
                 </el-table-column>
                 <el-table-column label="DownloadAddress">
                     <template slot-scope="scope">
                        <el-button type="primary" @click="download(scope.row.url)">download</el-button>
                     </template>
                 </el-table-column>
                 <el-table-column  label="Enable">
                     <template slot-scope="scope">
                        <el-switch v-model="scope.row.enable" active-color="#13ce66" inactive-color="#ccc" @change="changeEnable(scope.row)"></el-switch>
                     </template>
                 </el-table-column>
                   <el-table-column label="operation">
                     <template slot-scope="scope">
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

    </div>

</template>

<script>
export default{
    name:"FileManagement",
     data() {
        return{
         tableData:[],
          name:'',
          pageNum:1,
          pageSize:10,
          total:0,
          multipleSelection:[],

        }
      },
      created(){
        this.load()
      },
    methods:{
     load(){
          this.request.get("/file/page",{
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
        },

        reset(){
            this.filename="",
            this.load()
        },
        changeEnable(row){
            this.request.post("/file/update",row).then(res =>{
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

        handlerDelete(id){
            this.request.delete("/file/"+id).then(res =>{
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
          this.request.post("/file/del/batch",ids).then(res =>{
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
        handleFileUploadSuccess(res){
            console.log(res)
            this.load()
        },
        download(url){
            window.open(url)
        }




    }
}
</script>
<style scoped>
</style>