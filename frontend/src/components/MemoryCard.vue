<template>
  <div class="card" @click="flipCard">
    <div v-if="flipped">{{ value }}</div>
    <div v-else>?</div>
  </div>
</template>

<script>
export default {
  props: {
    value: {
      type: Number,
      required: true
    },
    flipped: {
      type: Boolean,
      required: true
    },
    id: {
      type: Number,
      required: true
    }
  },
  methods: {
    flipCard() {
      this.$emit('flip', this.id);
    }
  }
}
</script>

<style scoped>
.card {
  width: 100px;
  height: 100px;
  margin: 5px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: white;
  border: 2px solid #4CAF50;
  cursor: pointer;
  user-select: none;
}

.card:hover {
  background-color: #f9f9f9;
}
</style>
