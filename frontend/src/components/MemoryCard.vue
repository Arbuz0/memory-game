<template>
  <div class="card" @click="flipCard" :class="{ flipped: flipped }">
    <div class="card-inner">
      <div class="card-front">
        ?
      </div>
      <div class="card-back">
        <img :src="image" />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    image: {
      type: String,
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
  perspective: 1000px; /* Perspective for 3D effect */
  cursor: pointer;
}

.card-inner {
  width: 100%;
  height: 100%;
  position: relative;
  transform-style: preserve-3d;
  transition: transform 0.6s; /* Duration of the flip animation */
}

.card.flipped .card-inner {
  transform: rotateY(180deg); /* Rotate the card on the Y axis */
}

.card-front, .card-back {
  width: 100%;
  height: 100%;
  position: absolute;
  backface-visibility: hidden; /* Hide the back face of the card */
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 10px; /* Rounded corners */
}

.card-front {
  transform: rotateY(0deg); /* Front face remains in place */
  background-color: #4CAF50; /* Color for the front of the card */
  color: white; /* Text color */
  font-size: 24px; /* Font size */
}

.card-back {
  transform: rotateY(180deg); /* Back face is rotated 180 degrees */
}

.card img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 10px; /* Rounded corners for image */
}
</style>
