// LoginPage.jsx
import React from 'react';
import { useNavigate } from 'react-router-dom';
import './Welcome.css';

const Welcome = () => {
  const navigate = useNavigate();

  const redirectToUrl = () => {
    const userId = document.getElementById('userId').value;
    if (userId > 0 && userId <= 100) {
      const newUrl = `/id`;
      navigate(newUrl);
    } else {
      alert('Please enter a valid ID');
    }
  };

  const redirectToLogin = () => {
    navigate('/login');
  };

  const redirectToNewUser = () => {
    navigate('/reg');
  };

  const redirectToAll = () => {
    navigate('/All');
  };

  const redirectToLogout = () => {
      window.location.replace( 'https://localhost:8080/api/logout');
  };

  const redirectToProfile = () => {
    navigate('/profile');
  };

  const redirectToAudioList = () => {
    navigate('/audio_player');
  };

  return (
    <div className="container">
      <button className="top-left-button" onClick={redirectToLogin}>
        Зайти
      </button>
      <div className="form-container">
        <h1>Enter ID</h1>
        <input type="text" id="userId" placeholder="Enter your ID" />
        <button onClick={redirectToUrl}>Go</button>
      </div>
      <button className="Reg_button" onClick={redirectToNewUser}>
        Зарегаться
      </button>
      <button className="All_button" onClick={redirectToAll}>
        All
      </button>
      <button className="top-left-button4" onClick={redirectToLogout}>
        Выйти
      </button>
      <button className="top-left-button5" onClick={redirectToProfile}>
        Профиль
      </button>
      <button className="top-left-button3" onClick={redirectToAudioList}>
        Музон
      </button>
    </div>
  );
};

export default Welcome;
