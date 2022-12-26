import React from "react";

interface TextFiledInterface {
    type: string,
    placeholder: string,
    id: string,
    value: string,
    setValue: React.Dispatch<React.SetStateAction<string>>,

}

const TextField:React.FC<TextFiledInterface> = (props) => {

    return (
        <div className="mb-4">
            <input
                value={props.value}
                onChange={
                    (e) => props.setValue(e.target.value)
                }
                id={props.id}
                type={props.type}
                className="form-control w-1/5 px-3 py-1.5 text-base font-normal text-gray-700 bg-white bg-clip-padding border border-solid border-gray-300 rounded transition ease-in-out m-0 focus:text-gray-700 focus:bg-white focus:border-blue-600 focus:outline-none"
                placeholder={props.placeholder}
            />
        </div>
    )

}
export default TextField;