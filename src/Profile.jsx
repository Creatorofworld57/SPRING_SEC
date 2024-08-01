import React, { useEffect, useState } from 'react';
import './Profile.css';
import { useNavigate } from 'react-router-dom';

const Profile = () => {
    const [imagePath, setImagePath] = useState('12');
    const navigate = useNavigate();

    useEffect(() => {
        getUserInfo();
    }, []);

    const getUserInfo = async () => {
        try {
            const response = await fetch('https://localhost:8080/api/userInfo', {
                headers: {
                    'Cache-Control': 'no-cache',
                },
                credentials:"include"
            });
            if (response.ok) {
                const data = await response.text();
                setImagePath(data);
            } else if (response.status === 304) {
                console.log('Resource not modified.');
            } else {
                console.error('Error fetching user info', response.status);
            }
        } catch (error) {
            console.error('Error fetching user info', error);
        }
    };

    const redirectTo = (url) => {
        navigate(url);
    };

    const redirectToDelete = async () => {
        try {
            if (window.confirm('Вы уверены, что хотите удалить свою учетную запись?')) {
                const response = await fetch('https://localhost:8080/api/user', {
                    method: 'DELETE',
                    credentials:'include'
                });
                if (response.ok)
                    window.location.replace('https://localhost:8080/api/logout');

            }
        } catch (error) {
            console.error('Error deleting', error);
            alert('Error deleting.');
        }
    };

    return (
        <div>
            <div className="image-container">
                <img id="myImage" className="image" src={`https://localhost:8080/api/images/${imagePath}`} alt="Profile" />
            </div>
            <button className="top-left-button2" onClick={() => redirectTo('/')}>Назад</button>
            <button className="top-left-button1" onClick={redirectToDelete}>Удалить учетную запись</button>
            <button className="Up_button" onClick={() => redirectTo('/update')}>Update my info</button>
        </div>
    );
}

export default Profile;
