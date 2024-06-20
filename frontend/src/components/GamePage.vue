<template>
  <div class="game-page">
    <h1>Memory Game</h1>
    <div class="game-board">
      <MemoryCard
        v-for="(card, index) in cards"
        :key="index"
        :value="card.value"
        :isFlipped="card.isFlipped || matchedCards.includes(card.value)"
        @flip="handleCardFlip(index)"
      />
    </div>
    <div class="back-button">
      <router-link to="/">
        <button>Back to Main Menu</button>
      </router-link>
    </div>
  </div>
</template>

<script>
import MemoryCard from '@/components/MemoryCard.vue';

export default {
  name: 'GamePage',
  components: {
    MemoryCard
  },
  data() {
    return {
      cards: this.shuffleCards(),
      flippedIndices: [],
      matchedCards: []
    };
  },
  methods: {
    shuffleCards() {
      let values = Array.from({ length: 12 }, (_, i) => i + 1).flatMap(i => [i, i]);
      for (let i = values.length - 1; i > 0; i--) {
        const j = Math.floor(Math.random() * (i + 1));
        [values[i], values[j]] = [values[j], values[i]];
      }
      return values.map(value => ({ value, isFlipped: false }));
    },
    handleCardFlip(index) {
      if (this.flippedIndices.length < 2 && !this.cards[index].isFlipped) {
        this.cards[index].isFlipped = true;
        this.flippedIndices.push(index);

        if (this.flippedIndices.length === 2) {
          const [firstIndex, secondIndex] = this.flippedIndices;
          if (this.cards[firstIndex].value === this.cards[secondIndex].value) {
            this.matchedCards.push(this.cards[firstIndex].value);
            this.flippedIndices = [];
          } else {
            setTimeout(() => {
              this.cards[firstIndex].isFlipped = false;
              this.cards[secondIndex].isFlipped = false;
              this.flippedIndices = [];
            }, 1000);
          }
        }

        if (this.matchedCards.length === this.cards.length / 2) {
          this.saveGameResult('Won');
        }
      }
    },
    saveGameResult(result) {
      const moves = this.cards.filter(card => card.isFlipped).length;
      const gameHistory = JSON.parse(localStorage.getItem('gameHistory')) || [];
      gameHistory.push(`Game ${gameHistory.length + 1}: ${result} in ${moves} moves`);
      localStorage.setItem('gameHistory', JSON.stringify(gameHistory));
    }
  }
};
</script>

<style scoped>
.game-page {
  text-align: center;
  margin-top: 50px;
}

.game-board {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  grid-gap: 10px;
  justify-content: center;
  margin: 20px auto;
  padding: 20px;
  background-color: #ececec; /* Board background color */
  border-radius: 10px;
  width: fit-content;
}

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

.back-button {
  margin-top: 20px;
}

button {
  padding: 10px 20px;
  font-size: 16px;
}
</style>
