<template>
    <div>
    <el-row :gutter="10" style="margin-bottom: 40px ;padding:5px">
        <el-col :span="6">
            <el-card style="color:#409EFF">
            <div><i class="el-icon-user-solid" /> user size</div>
            <div style ="padding : 10px 0; text-align:center;font-weight: bold"></div>
            <el-tag type="primary" style="font-size:2opx">100</el-tag>
            </el-card>
        </el-col>

        <el-col :span="6">
              <el-card style="color:#F56C6D">
                   <div ><i class="el-icon-money" /> sales</div>
                   <div style ="padding : 10px 0; text-align:center;font-weight: bold"></div>
                   $100000
               </el-card>
         </el-col>
        <el-col :span="6">
              <el-card style="color:#F56C6D">
                   <div ><i class="el-icon-bank-card" /> benefit</div>
                   <div style ="padding : 10px 0; text-align:center;font-weight: bold"></div>
                   100
               </el-card>
        </el-col>
         <el-col :span="6">
                <el-card style="color:#409EFF">
                     <div ><i class="el-icon-s-shop" />customer</div>
                     <div style ="padding : 10px 0; text-align:center;font-weight: bold"></div>
                     100
                 </el-card>
         </el-col>

    </el-row>
    <el-row style="padding:10px">
        <el-col :span="12">
          <div id="main" style="width: 500px; height: 400px"></div>
        </el-col>
        <el-col :span="12">
            <div id="pie" style="width: 500px; height: 400px"></div>
        </el-col>
    </el-row>
    </div>

</template>

<script>
import * as echarts from 'echarts'
export default{
    name:"Home",
    data(){
        return{

        }
    },
    mounted(){//page element show and executed

        var option = {
          xAxis: {
            type: 'category',
            data: ["first quarter","second quarter","third quarter","fourth quarter"]
          },
          yAxis: {
            type: 'value'
           },

          tooltip: {
            trigger: 'item'
          },
          legend: {
              orient: 'vertical',
              left: 'left'
           },
          series: [
            {
              name:'123',
              data: [],
              type: 'bar'
            },
            {
              name:'456',
              data: [],
              type: 'line'
            },
            {
              name:'789',
              data: [],
              type: 'bar'
            },
            {
              name:'789',
              data: [],
              type: 'line'
            }
          ]
        };
        var pieDom = document.getElementById('pie');
        var pieChart = echarts.init(pieDom);
        var chartDom = document.getElementById('main');
        var myChart = echarts.init(chartDom);
            var pieOption = {
                  title: {
                    text: 'Referer of a Website',
                    subtext: 'Fake Data',
                    left: 'center'
                  },
                  tooltip: {
                    trigger: 'item'
                  },
                  legend: {
                    orient: 'vertical',
                    left: 'left'
                  },
                  series: [
                    {
                      name:"starbuck",
                      type: 'pie',
                      //pie radius
                      radius: '50%',
                      center:['25%','50%'],
                      label:{
                        normal:{
                            show:true,
                            position:'inner',
                            textStyle:{
                                fontWeight:300,
                                fontSize:16,
                                color:'#fff'

                            },
                            formatter:'{d}%'

                        }
                      },

                      data: [],
                      emphasis: {
                        itemStyle: {
                          shadowBlur: 10,
                          shadowOffsetX: 0,
                          shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                      }
                    },
                     {
                    name:"coffee",
                    type: 'pie',
                    //pie radius
                    radius: '50%',
                    center:['75%','50%'],
                    label:{
                      normal:{
                          show:true,
                          position:'inner',
                          textStyle:{
                              fontWeight:300,
                              fontSize:16,
                              color:'#fff'

                          },
                          formatter:'{d}%'

                      }
                    },

                    data: [ {name:"firstQuarter" , value:5},
                           {name:"secondQuarter" , value:6},
                           {name:"thirdQuarter"  , value:7},
                           {name:"fourthQuarter" , value:8}
                           ],


                  }
                  ],


                };

        this.request.get("/echarts/members").then(res =>{
           // option.xAxis.data=res.data.x
           //123 data
            option.series[0].data=res.data
            option.series[1].data=res.data
            //456 data
            option.series[2].data=[5,6,7,8]
            option.series[3].data=[5,6,7,8]

            myChart.setOption(option);

          pieOption.series[0].data=[
                {name:"firstQuarter" , value:res.data[0]},
                {name:"secondQuarter" , value:res.data[1]},
                {name:"thirdQuarter" , value:res.data[2]},
                {name:"fourthQuarter" , value:res.data[3]},
          ]
           pieChart.setOption(pieOption)

        })


    }

}
</script>
<style scoped>
</style>