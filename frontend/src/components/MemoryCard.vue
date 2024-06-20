<template>
  <div class="card" @click="flipCard">
    <div :class="{'flipped': isFlipped}">
      <div class="front">?</div>
      <div class="back">{{ value }}</div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'MemoryCard',
  props: {
    value: Number,
    isFlipped: Boolean
  },
  methods: {
    flipCard() {
      if (!this.isFlipped) {
        this.$emit('flip');
      }
    }
  }
};
</script>

<style scoped>
.card {
  width: 100px;
  height: 100px; /* Adjust height to make the cards square */
  perspective: 1000px;
  cursor: pointer;
}

.card > div {
  position: relative;
  width: 100%;
  height: 100%;
  text-align: center;
  transition: transform 0.5s;
  transform-style: preserve-3d;
}

.card .front,
.card .back {
  position: absolute;
  width: 100%;
  height: 100%;
  backface-visibility: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 2em;
}

.card .front {
  background: #2e3b4e;
  color: white;
}

.card .back {
  background: white;
  color: black;
  transform: rotateY(180deg);
}

.flipped {
  transform: rotateY(180deg);
}
</style>
