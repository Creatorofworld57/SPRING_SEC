import React, { useState } from 'react';
import './Update.css';
import {useNavigate} from "react-router-dom";
const Update = () => {
    const [name, setName] = useState('');
    const [password, setPassword] = useState('');
    const [file, setFile] = useState(null);
    const navigate = useNavigate();
    const handleSubmit = async (event) => {
        event.preventDefault();


        const formData = new FormData();
        formData.append('name', name);
        formData.append('password', password);
        if (file) {
            formData.append('file', file);
        }

        const json = JSON.stringify({ name, password });
        formData.append('json', new Blob([json], { type: 'application/json' }));

        const response = await fetch('https://localhost:8080/api/user', {
            method: 'PATCH',
            body: formData,
            credentials: "include"
        });

        if (response.ok) {
            alert('Data sent successfully!');

        } else {
            alert('Failed to send data.');
        }
    };
    const useRedirect = () =>{
        navigate('/')
    }
    return (
        <div className="form-container">
            <h1>Enter Data</h1>
            <form onSubmit={handleSubmit} encType="multipart/form-data">
                <input
                    type="text"
                    id="name"
                    name="name"
                    placeholder="Enter your Name"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    required
                />
                <input
                    type="password"
                    id="password"
                    name="password"
                    placeholder="Enter your Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />
                <div className="file-input-container">
                    <label className="file-input-label" htmlFor="file">Выберите новый аватар</label>
                    <input
                        type="file"
                        id="file"
                        name="file"
                        onChange={(e) => setFile(e.target.files[0])}
                        required
                    />
                </div>
                <button type="submit">Send</button>
            </form>
            <button className="Back" onClick={useRedirect} >Назад</button>
        </div>
    );
};

export default Update;
