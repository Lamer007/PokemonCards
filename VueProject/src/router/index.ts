import { createRouter, createWebHistory } from 'vue-router'
import Home from '../pages/Home.vue'
import Collection from '../pages/Collection.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      component: Home,
      meta: {
        title: 'Open packs'
      }
    },
    {
      path: '/Collection',
      component: Collection,
      meta: {
        title: 'Collection'
      }
    }
  ],
})

router.afterEach((to, from, next) => {
  if (to.meta.title) {
    document.title = `${to.meta.title}`
  }
})

export default router
