import { defineStore } from 'pinia'
import { useAuthStore } from "./AuthStore.js"

const APP_DOMAIN = "https://localhost"

export const useHistoryStore = defineStore('history', {
  state: () => ({
    options: [-5,-4,-3,-2,-1,0,1,2,3],
    x: '',
    y: '',
    r: '',
    history: [],
    groups: [],
    currentGroup: undefined,
    isGroupCreating: true,
    chosenHitsIds: [],
    newGroupName: '',
    newGroupDesc: '',
  }),

  getters: {
    reversedHistory: (state) => state.history.slice().reverse().filter(item => state.currentGroup === undefined || state.isGroupCreating || state.currentGroup.hits.includes(item.id)),
    hitsOnly: (state) => state.history.filter(item => item.hit),
    groupNames: (state) => state.groups.map(item => item.name),

  },

  actions: {
    async set_history_from_backend() {
      try {
        const authStore = useAuthStore()
        const response = await fetch(APP_DOMAIN + '/geometry/hit', {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': authStore.auth_header
          }
        })

        if (response.status === 401 && authStore.refresh_token()) {
          this.set_history_from_backend()
        }

        if (!response.ok) {
          throw new Error(`Ошибка запроса: ${response.status}`)
        }

        const data = await response.json()
        this.history = Array.isArray(data) ? data : data.data || []
      } catch (error) {
        console.error('Ошибка при загрузке истории:', error)
      }
    },
    async setGroupsFromBackend() {
        try {
            const authStore = useAuthStore()
            const response = await fetch(APP_DOMAIN + '/geometry/group', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': authStore.auth_header
                }
            })

            if (response.status === 401 && authStore.refresh_token()) {
                this.setGroupsFromBackend()
            }

            if (!response.ok) {
                throw new Error(`Ошибка запроса: ${response.status}`)
            }

            const data = await response.json()
            this.groups = Array.isArray(data) ? data : data.data || []
        } catch (error) {
            console.error('Ошибка при загрузке истории:', error)
        }
    },

    async addGroup(){
        const authStore = useAuthStore()

        const group = {name: this.newGroupName, description: this.newGroupDesc, hits: this.chosenHitsIds};

        const response = await fetch(APP_DOMAIN + '/geometry/group', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': authStore.auth_header
            },
            body: JSON.stringify(group)
        })

        if (response.status === 401 && authStore.refresh_token()) {
            this.addGroup(group)
        }

        if (!response.ok) {
            throw new Error(`Ошибка запроса: ${response.status}`)
        }
        this.groups.push(group)
    },

    async addRecord() {
      if (this.options.includes(this.r) && this.r > 0 && this.options.includes(this.x) && this.y !== '' && this.y >= -3 && this.y <= 5) {
        try {
          const authStore = useAuthStore()
          const response = await fetch(APP_DOMAIN + '/geometry/hit', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
              'Authorization': authStore.auth_header
            },
            body: JSON.stringify({x: this.x, y: this.y, r: this.r})
          })
          
          if (response.status === 401 && authStore.refresh_token()) {
            this.addRecord()
          }

          if (!response.ok) {
            throw new Error(`Ошибка при добавлении записи: ${response.status}`)
          }

          const newRecord = await response.json()
          this.history.push(newRecord)
        } catch (error) {
          console.error('Ошибка при добавлении записи:', error)
        }
      } else {
        throw new Error("invalid parameters")
      }


    },
    clear() {
      this.history = []
    },
    clear_input() {
      this.x = ''
      this.y = ''
      this.r = ''
    }
  }
})
