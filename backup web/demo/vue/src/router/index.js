import Vue from 'vue'
import VueRouter from 'vue-router'
import store from "@/store"


Vue.use(VueRouter)

const routes = [

  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
   {
      path: '/login',
      name: 'Login',

     component:()=>import('../views/Login.vue')
    },
     {
          path: '/register',
          name: 'Register',

         component:()=>import('../views/Register.vue')
      },


  



         {
            path: '/',
            name: '404',

           component:()=>import('../views/404.vue')
          }

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
export const resetRoutes = () =>{
    router.matcher = new VueRouter({
        mode:'history',
        base:process.env.BASE_URL,
        routes
    })
}
export const setRoutes =() =>{
    const storeMenus = localStorage.getItem("menus");
    if(storeMenus){

            const currentRoutesNames =router.getRoutes().map(v=>v.name)
            if(!currentRoutesNames.includes('Manager')){
              const manageRoute={
                        path:'/',
                        name:'Manager',
                        component: () => import('../views/Manager.vue'),
                        redirect:"/home",
                        children:[
                            {path:'person',name:'Manage',component:()=>import('../views/Person.vue')},
            //                {path:'password',name:'Change password',component:()=>import('../views/Password.vue')},

                        ]}
                        const menus =JSON.parse(storeMenus)
                        menus.forEach(item =>{
                            if(item.path){
                            let itemMenu = {path: item.path.replace("/",""),name:item.name,component:()=>import('../views/'+item.pagePath+'.vue')}
                            manageRoute.children.push(itemMenu)
                            }
                            else if(item.children.length){
                                item.children.forEach(item =>{
                                    if(item.path){
                                        let itemMenu = {path: item.path.replace("/",""),name:item.name,component:()=>import('../views/'+item.pagePath+'.vue')}
                                        manageRoute.children.push(itemMenu)}
                                })


                            }
                        })

                router.addRoute(manageRoute)

            }



    }

}
setRoutes()
//route defender
router.beforeEach((to,from,next)=>{
    localStorage.setItem("currentPathName",to.name)
//    store.commit("setPath")

    if(!to.matched.length){
        const storeMenus=localStorage.getItem("menus")
        if(storeMenus){
             next("/404")
        }else{
             next("/login")
        }

    }
  next()

})

export default router
