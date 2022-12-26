import React from "react";
import {Link, useNavigate} from 'react-router-dom';

interface ButtonInterface {
    name: string,
    link: string;
    id: string,

}

const Button:React.FC<ButtonInterface> = (props) => {

    const navigate = useNavigate();


    return (
                <button type="submit"
                    id={props.id}
                    className={"bg-blue-500 w-1/3 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"}>
                {props.name}
            </button>
        )

}

export default Button;