import React, { useState, useEffect, useRef } from 'react';
import './Styles/Audio.css'; // Импортируйте созданный CSS файл
import { useNavigate } from 'react-router-dom';

const AudioPlayer = () => {
  const [counter, setCounter] = useState(152);
  const [header, setHeader] = useState('Loading header...');
  const [audioCounter, setAudioCounter] = useState(452);
  const audioRef = useRef(null);
  const navigate = useNavigate();

  useEffect(() => {
    loadCounter();
  }, []);

  useEffect(() => {
    loadAudio(counter);
  }, [counter]);

  const loadCounter = async () => {
    try {
      const response = await fetch('https://localhost:8080/api/audioCount', { method: 'GET' });
      const count = await response.text();
      setAudioCounter(Number(count));
    } catch (error) {
      console.error('Error fetching audio count:', error);
    }
  };

  const loadAudio = async (counter) => {
    const audioUrl = `https://localhost:8080/api/audio/${counter}`;
    const audioType = 'audio/mp3';

    if (audioRef.current) {
      const audioElement = audioRef.current;

      // Удаляем все текущие source элементы
      while (audioElement.firstChild) {
        audioElement.removeChild(audioElement.firstChild);
      }

      // Создаем новый элемент source
      const sourceElement = document.createElement('source');
      sourceElement.src = audioUrl;
      sourceElement.type = audioType;

      // Добавляем новый source элемент в элемент аудио
      audioElement.appendChild(sourceElement);

      // Устанавливаем название трека (если есть)
      try {
        const response = await fetch(`https://localhost:8080/api/audioName/${counter}`, { method: 'GET' });
        setHeader(await response.text());
      } catch (error) {
        console.error('Error fetching audio name:', error);
        setHeader('Error loading header');
      }

      // Перезагружаем аудио для его воспроизведения с новым источником
      audioElement.load();
      audioElement.volume = 0.1;

      // Воспроизводим аудио, когда оно готово
      audioElement.oncanplaythrough = () => {
        audioElement.play().catch(error => {
          console.error('Error playing audio:', error);
        });
      };
    }
  };

  const nextAudio = () => {
    setCounter((prevCounter) => ( prevCounter !== audioCounter ? prevCounter+50 : prevCounter));
  };

  const prevAudio = () => {
    setCounter((prevCounter) => (prevCounter !== 152 ? prevCounter - 50 : prevCounter));
  };

  const redirectTo = (url) => {
    navigate(url);
  };

  return (
      <div>
        <audio controls id="myAudio" ref={audioRef} onEnded={nextAudio}>
          <source src="" type="audio/mp3" />
        </audio>
        <div>
          <button type="button" className="button10" id="first" onClick={nextAudio}>Следующий</button>
          <button type="button" className="button11" id="second" onClick={prevAudio}>Предыдущий</button>
         <button className="top-left-button" onClick={() => redirectTo('/')}>Назад</button>
        </div>
        <div className="form-container">
          <div id="header-value">{header}</div>
        </div>
      </div>
  );
};

export default AudioPlayer;
