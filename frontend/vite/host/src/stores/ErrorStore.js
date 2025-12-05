import { defineStore } from 'pinia'

export const useErrorStore = defineStore('error', {
    state: () => ({
        errorMsg: '',
        errorTitle: ''
    }),

    actions: {
        setErrorMsg (msg) {
            this.errorMsg = msg
        },
        setErrorTitle (title) {
            this.errorTitle = title
        }
    }
})
