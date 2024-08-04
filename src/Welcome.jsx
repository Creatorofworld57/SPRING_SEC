import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Styles/Welcome.css';


const Welcome = () => {
    const navigate = useNavigate();
    const [isButtonVisible, setButtonVisible] = useState(true);

    const [inputValue, setInputValue] = useState();

    const redirectToUrl =  () => {
        if (inputValue > 0 && inputValue <= 100 ) {

            navigate('/id', { state: { inputValue } });
        }

        else {
            alert('Please enter a valid ID');
        }
    };

    useEffect(() => {
        loginButton();
    }, []);

    const loginButton = async () => {
        try {
            const response = await fetch('https://localhost:8080/api/authorization', {
                method: 'GET',
                credentials: 'include'
            });
            if (response.status === 200) {
                setButtonVisible(false);
            } else {
                setButtonVisible(true);
            }
            console.log(isButtonVisible)
        } catch (error) {
            console.error('Failed to fetch authorization:', error);
        }
    };

    const redirectToLogin = () => {
        navigate('/login');
    };

    const redirectToAll = () => {
        navigate('/All');
    };

    const redirectToProfile =  () => {


            if (!isButtonVisible) {
                navigate('/profile');
            } else {
                navigate('/login');
            }

        };

    const redirectToAudioList = () => {
        navigate('/audio_player');
    };

    const handleInputChange = (event) => {
        setInputValue(event.target.value);
    };

    return (
        <div className="container">
            <button className={isButtonVisible?"top":"top active"} onClick={redirectToLogin}>
                    Sign in or sign up
                </button>
            <div className="form-container">
                <h1>Enter ID</h1>
                <input
                    type="text"
                    id="userId"
                    placeholder="Enter your ID"
                    value={inputValue}
                    onChange={handleInputChange}
                />
                <button onClick={redirectToUrl}>Go</button>
            </div>
            <button className="All_button" onClick={redirectToAll}>
                All
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
