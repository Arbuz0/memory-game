import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';

const url = `${window.location.protocol}//${window.location.hostname}:8080`;

let stompClient = null;

const connectToSocket = (gameId, onMessageReceived, onConnected, onError) => {
  console.log("Connecting to the game");

  const socket = new SockJS(url + "/gameplay");
  stompClient = Stomp.over(socket);

  stompClient.connect({}, (frame) => {
    console.log("Connected to the frame: " + frame);
    if (onConnected) onConnected(frame);
    stompClient.subscribe(`/topic/game-progress/${gameId}`, (response) => {
      const data = JSON.parse(response.body);
      console.log(data);
      if (onMessageReceived) onMessageReceived(data);
    });
  }, (error) => {
    console.error('WebSocket connection error:', error);
    if (onError) onError(error);
  });
};

const sendMessage = (gameId, message) => {
  if (stompClient && stompClient.connected) {
    const topic = '/app/gameplay';
    const payload = { gameId, ...message };
    console.log(`Sending message to topic: ${topic}`, payload);
    stompClient.send(topic, {}, JSON.stringify(payload));
  } else {
    console.error('Send message failed: WebSocket is not connected');
  }
};

const disconnect = () => {
  if (stompClient) {
    console.log('Disconnecting WebSocket...');
    stompClient.disconnect(() => {
      console.log('WebSocket disconnected');
    });
  }
};

export default {
  connectToSocket,
  sendMessage,
  disconnect,
};
