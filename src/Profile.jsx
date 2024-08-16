import React, { useEffect, useState } from 'react';
import './Styles/Profile.css';
import { useNavigate } from 'react-router-dom';
import Menu from './Menu';
import { FaGithub, FaTelegramPlane } from 'react-icons/fa'; // Импорт иконок

const Profile = () => {
    const navigate = useNavigate();
    const [menuActive, setMenuActive] = useState(false);
    const [isChecked, setIsChecked] = useState(false);
    const [url1, setUrl1] = useState('');
    const [url2, setUrl2] = useState('');


    const redirectTo = (url) => {
        navigate(url);
    };

    const userSocials = async () => {
        try {
            const response = await fetch('https://localhost:8080/api/socials', {
                method: 'GET',
                credentials: 'include'
            });
            const user = await response.json();
            setUrl1(user.telegram);
            setUrl2(user.git);
        } catch (error) {
            console.error('Error:', error);
        }
    };



    useEffect(() => {

        userSocials();

        // Устанавливаем начальное значение isChecked в зависимости от цвета фона
        const initialBackgroundColor = getComputedStyle(document.body).backgroundColor;
        setIsChecked(initialBackgroundColor === 'rgb(46, 46, 46)'); // Проверка на темный цвет
    }, []);

    const handleChange = (event) => {
        const checked = event.target.checked;
        setIsChecked(checked);

        document.body.style.backgroundColor = checked ? '#2e2e2e' : 'lightgrey';

    };

    return (
        <div>
            <nav>
                <div className='burger-btn' onClick={() => setMenuActive(!menuActive)}>
                    <span className={menuActive ? 'line1 active' : 'line1'}/>
                    <span className={menuActive ? 'line2 active' : 'line2'}/>
                    <span className={menuActive ? 'line3 active' : 'line3'}/>
                </div>
            </nav>

            <main>
                <div>

                </div>
            </main>
            <button className="back_up" onClick={() => redirectTo('/')}></button>
            <div className="toggle-switch">
                <input
                    type="checkbox"
                    id="toggle"
                    className="toggle-input"
                    checked={isChecked}
                    onChange={handleChange}
                />
                <label htmlFor="toggle" className="toggle-label"></label>
            </div>
            <Menu active={menuActive} setActive={setMenuActive}/>
            {url1 && (
                <div className="link-preview">
                    <a href={url1} target="_blank" rel="noopener noreferrer">
                        <FaTelegramPlane/> {url1}
                    </a>
                </div>
            )}
            {url2 && (
                <div className="link-preview1">
                    <a href={url2} target="_blank" rel="noopener noreferrer">
                        <FaGithub/> {url2}
                    </a>
                </div>
            )}
        </div>
    );
};

export default Profile;
