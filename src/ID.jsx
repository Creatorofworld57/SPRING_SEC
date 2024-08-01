import React from 'react';
import './ID.css'; // Импортируйте CSS файл
import {useNavigate} from "react-router-dom";

const Id = ({ list }) => {
    const useRedirect  = ()=>{
        useNavigate('/');
    }


    return (
        <div className="container">
            <table>
                <thead>
                <tr>
                    <th>Data</th>
                </tr>
                </thead>
                <tbody>
                {list.map((item, index) => (
                    <tr key={index}>
                        <td>{item}</td>
                    </tr>
                ))}
                </tbody>
            </table>

            <div>
                <form target="_blank" onSubmit={(e) => e.preventDefault()}>
                    <button type="but" onClick ={useRedirect}>Вернуться к поиску</button>
                </form>
            </div>
        </div>
    );
};

export default Id;
