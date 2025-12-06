<script>

import Table from './elements/Table.vue'
import GroupInput from './elements/GroupInput.vue'

export default {
  name: "ResultsContainer",
  components: { Table, GroupInput }
}
</script>

<template>
  <section class="results">
    <GroupInput />
    <h3>История проверок</h3>
    <Table />
  </section>
</template>

<style scoped>

.container {
  max-width: var(--max-width);
  margin: 0 auto;
  width: 100%;
}

.controls-and-canvas {
  display: flex;
  gap: 18px;
  align-items: flex-start;
}

/* Desktop (>=1119px)*/
@media (min-width: 1119px) {
  .controls-and-canvas {
    flex-direction: row;
  }

  .controls {
    width: 320px;
  }

  .canvas-wrap {
    flex: 1;
  }
}

/* Tablet (>=885px and <1119px) */
@media (min-width: 885px) and (max-width: 1118px) {
  .controls-and-canvas {
    flex-direction: column;
    align-items: stretch;
  }

  .controls {
    width: 100%;
  }

  .canvas-wrap {
    width: 100%;
  }
}

/* Mobile (<885px) */
@media (max-width: 884px) {
  .controls-and-canvas {
    flex-direction: column;
    align-items: stretch;
    gap: 12px;
  }

  .controls,
  .canvas-wrap {
    width: 100%;
  }

  .buttons {
    flex-direction: column;
  }

  .legend {
    flex-direction: column;
    align-items: flex-start;
    gap: 6px;
  }

  button {
    width: 100%;
  }
}

/* Shared styles */
.controls {
  background: #fff;
  padding: 16px;
  border-radius: 12px;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.06);
}

.canvas-wrap {
  display: flex;
  flex-direction: column;
  background: #fff;
  padding: 16px;
  border-radius: 12px;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.06);
  align-items: center;
}

canvas {
  background: white;
  border: 1px solid #ddd;
}

.legend {
  display: flex;
  gap: 12px;
  margin-top: 8px;
}

.dot {
  display: inline-block;
  width: 12px;
  height: 12px;
  border-radius: 50%;
}

.dot.hit {
  background: #27d13b;
}
.dot.miss {
  background: red;
}
.dot.in.progress {
  background: black;
}

.buttons {
  display: flex;
  gap: 8px;
  margin-top: 12px;
}

button {
  padding: 8px 12px;
  border-radius: 8px;
  border: none;
  background: #2b6cb0;
  color: white;
}

button.muted {
  background: #e2e8f0;
  color: #333;
}

.validation {
  color: #b91c1c;
  margin-top: 8px;
}

.results {
  margin-top: 22px;
  background: #fff;
  padding: 12px;
  border-radius: 12px;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th,
td {
  padding: 8px;
  border-bottom: 1px solid #eee;
  text-align: left;
}

td.hit {
  color: green;
  font-weight: 600;
}

td.miss {
  color: red;
}

.xyr {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

button:hover {
  cursor: pointer;
}

</style>
