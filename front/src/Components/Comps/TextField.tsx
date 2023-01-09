import React from "react";

interface TextFiledInterface {
    type: string,
    placeholder: string,
    id: string,
    value: string|undefined|number,
    setStringValue: React.Dispatch<React.SetStateAction<string>>|undefined,
    setNumberValue: React.Dispatch<React.SetStateAction<number>>|undefined,


}

/**
 * This is a text field component for the app.
 * @param props of the text-field that are passed through the interface is extends.
 */
const TextField:React.FC<TextFiledInterface> = (props) => {

    return (
        <div className="mb-4">
            <input
                value={props.value}
                onChange={
                    (e) =>
                    {
                        if (props.type === "text" || props.type === "password") {
                            props.setStringValue!(e.target.value)
                        } else {
                            props.setNumberValue!(Number(e.target.value))
                        }
                    }
                }
                id={props.id}
                type={props.type}
                className="form-control w-1/5 px-3 py-1.5 text-base font-normal text-gray-700 bg-white bg-clip-padding border border-solid border-gray-300 rounded transition ease-in-out m-0 focus:text-gray-700 focus:bg-white focus:border-blue-600 focus:outline-none"
                placeholder={props.placeholder}
                min="0"
            />
        </div>
    )

}
export default TextField;