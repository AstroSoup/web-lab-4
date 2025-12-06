<script>
import { useHistoryStore } from 'host/HistoryStore'
import {mapState, mapWritableState} from 'pinia'

export default {
  name: "Table",
  computed: {
    ...mapState(useHistoryStore, [
      'history',
      'reversedHistory',
      'isGroupCreating'
    ]),
    ...mapWritableState(useHistoryStore, ['chosenHitsIds'])
  },

  methods: {
    set_history_from_backend() {
      useHistoryStore().set_history_from_backend()
    },

    toggleRowSelection(hit) {
      if (!this.isGroupCreating) return;

      const id = hit.id;
      console.log(this.chosenHitsIds);
      if (this.chosenHitsIds.includes(id)) {
        this.chosenHitsIds = this.chosenHitsIds.filter(x => x !== id);
      } else {
        this.chosenHitsIds = [...this.chosenHitsIds, id];
      }
    },

    isSelected(hit) {
      return this.chosenHitsIds.includes(hit.id);
    }
  },

  mounted() {
    this.set_history_from_backend();
  }
}
</script>

<template>
  <table :class="{ selectable: isGroupCreating }">
    <thead>
    <tr>
      <th>X</th><th>Y</th><th>R</th><th>Попадание</th><th>Дата</th>
    </tr>
    </thead>
    <tbody>
    <tr
        v-for="(row, idx) in reversedHistory"
        :key="idx"
        :class="{ selected: isSelected(row) }"
        @click="toggleRowSelection(row)"
    >
      <td>{{ row.x }}</td>
      <td>{{ row.y }}</td>
      <td>{{ row.r }}</td>
      <td :class="{ hit: row.hit, miss: !row.hit }">
        {{ row.hit ? "Есть пробитие" : "Броня не пробита" }}
      </td>
      <td>{{ row.date }}</td>
    </tr>
    </tbody>
  </table>
</template>

<style>
table {
  border-collapse: collapse;
  width: 90%;
  margin: 20px auto;
  font-size: 14px;
}
th, td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: center;
}
th {
  background: #00aabb;
  color: white;
  font-weight: bold;
}
tr:nth-child(even) {
  background: rgba(255,255,255,0.6);
}
.hit { color: green; font-weight: bold; }
.miss { color: red; font-weight: bold; }

table.selectable tr {
  cursor: pointer;
}

.selected {
  background: rgba(0, 170, 187, 0.25) !important;
}
</style>
