<script>

import { useErrorStore } from 'host/ErrorStore'
import { useHistoryStore } from 'host/HistoryStore'
import ComboBox from "./ComboBox.vue";
import { mapWritableState, mapActions } from "pinia";

export default {
  name: "GroupInput",
  components: { ComboBox },
  data() {
    return {
    }
  },

  computed: {
    ...mapWritableState(useHistoryStore, ['isGroupCreating', 'groups', 'currentGroup', 'groupNames','newGroupName', 'newGroupDesc','chosenHitsIds'])
  },

  methods: {
    createGroup() {
      if (!this.newGroupName.trim()) {
        useErrorStore().errorTitle = "Не выбрано имя группы."
        useErrorStore().errorMsg = "Пожалуйста, введите имя группы."
        return;
      }
      this.addGroup()
      this.newGroupName = ''
      this.newGroupDesc = ''
      this.chosenHitsIds = []
    },
    ...mapActions(useHistoryStore, ['setGroupsFromBackend','addGroup'])
  },
  mounted() {
    this.setGroupsFromBackend();
  }
};
</script>

<template>
  <div v-if="isGroupCreating" class="create-group-container">
    <section class="controls">
      <h3> Создание группы </h3>

      <div class="fields">
        <div class="field">
          <label>Имя группы:</label>
          <input type="text" v-model="newGroupName" placeholder="Введите название группы" />
        </div>

        <div class="field">
          <label>Описание группы:</label>
          <textarea
              v-model="newGroupDesc"
              placeholder="Введите описание для группы"
          ></textarea>
        </div>
      </div>

      <div class="buttons">
        <button @click="createGroup">Создать Группу</button>
      </div>

    </section>
  </div>

  <div v-if="!isGroupCreating" class="choose-group-container">
    <section class="controls">
      <h3> Выбор существующей группы </h3>

      <ComboBox
          v-model="currentGroup"
          :options="groups"
          placeholder="Выберите группу"
      />
    </section>
  </div>

  <p class="switch-mode">
    <a href="#" @click.prevent="this.isGroupCreating = !this.isGroupCreating">
      {{ this.isGroupCreating ? "Выбрать группу из существующих" : "Создать новую группу" }}
    </a>
  </p>
</template>

<style scoped>

.create-group-container, .choose-group-container {
  max-width: 600px;
  margin: 0 auto;
  width: 100%;
}

@media (min-width: 885px) {
  .create-group-container {
    width: 600px;
  }
}

@media (max-width: 884px) {
  .buttons {
    flex-direction: column;
  }
  button {
    width: 100%;
  }
}

.controls {
  background: #fff;
  padding: 16px;
  border-radius: 12px;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.06);
}

.fields {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.field label {
  display: block;
  font-weight: 600;
  margin-bottom: 4px;
}
.switch-mode{
  text-align:center;
  margin-top:16px;
}
.switch-mode a{
  color:#2b6cb0;
  cursor:pointer;
  text-decoration:none;
  font-weight:bold;
}
input,
textarea {
  width: 95%;
  padding: 8px;
  border-radius: 8px;
  border: 1px solid #ddd;
}

textarea {
  height: 80px;
  resize: none;
}

.buttons {
  display:flex;
  gap: 8px;
  margin-top: 16px;
}

button {
  padding: 8px 12px;
  border-radius: 8px;
  border: none;
  background: #2b6cb0;
  color: white;
}

button:hover {
  cursor: pointer;
}

.validation {
  color: #b91c1c;
  margin-top: 8px;
}
</style>