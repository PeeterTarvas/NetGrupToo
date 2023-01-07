import React, {useEffect, useState} from "react";
import UserDto from "../../Dtos/UserDto";

interface onChange extends UserDto {
    onChange(user: UserDto):  Promise<void>
}

const User:React.FC<(onChange)> = (props) => {


    const [businessUserBorder, setBusinessUserBorder] = useState<string>("");
    const [maxItems, setMaxItems] = useState(props.maximum_items);

    function round(num: number, fractionDigits: number): number {
        return Number(num.toFixed(fractionDigits));
    }

    useEffect(
        () => {
            if (props.role === "ROLE_BUSINESS" && (props.cost! > 0)) {
                setBusinessUserBorder("border-amber-700 border-8")
            } else if (props.role === "ROLE_BUSINESS" && props.cost === 0) {
                setBusinessUserBorder("border-green-700 border-8")
            } else {
                setBusinessUserBorder("");
            }
        }, [props.role, props.cost]
    )

    return (
        <div className={`bg-blue-900 h-full justify-items-center content-center ${businessUserBorder}`}>

            <div>Name: {props.username}</div>
            <div>Email: {props.email}</div>
            <div>Role: {props.role}</div>
            <div>Number of free items: {props.maximum_items}</div>
            <div>Number of items: {props.number_of_items}</div>
            <div>
                {props.role === "ROLE_BUSINESS" ? (
                    <div>
                    <div className={"text-red-600"}>Needs to pay: {round(props.cost as number, 2)} euro(s) </div>
                        <div className="mb-4 w-full">
                            <input

                                value={maxItems}
                                onChange={
                                    (e) =>
                                    {
                                        const user: UserDto = {
                                            username: props.username,
                                            password: props.password,
                                            email: props.email,
                                            role: props.role,
                                            referenceUserUsername: props.referenceUserUsername,
                                            maximum_items: maxItems,
                                            cost: props.cost,
                                            number_of_items: props.number_of_items,
                                        }
                                        setMaxItems(Number(e.target.value));
                                        props.onChange(user).then(r => r);
                                        }
                                    }
                                type={"number"}
                                className="form-control w-1/2 px-3 py-1.5 text-base font-normal text-gray-700 bg-white bg-clip-padding border border-solid border-gray-300 rounded transition ease-in-out m-0 focus:text-gray-700 focus:bg-white focus:border-blue-600 focus:outline-none"
                                min="0"
                            />
                        </div>
                    </div>
                
                ) : ""}
            </div>
        </div>
    )


}

export default User;