import React, {useEffect, useState} from "react";
import UserDto from "../../Dtos/UserDto";
import ProgressBar from "./ProgressBar";

interface onChange extends UserDto {
    onChange(user: UserDto):  Promise<void>
}

/**
 * This is a component for representing a user in the app.
 * @param props of the user that extends the interface
 */
const User:React.FC<(onChange)> = (props) => {


    const [businessUserBorder, setBusinessUserBorder] = useState<string>("");
    const [maxItems, setMaxItems] = useState(props.maximum_items);
    const [itemsMap, setItemsMap] = useState<Map<string, number>>(new Map(Object.entries(props.items_status!)))

    /**
     * This function is for rounding numbers.
     * @param num is the number we want to round.
     * @param fractionDigits the decimal place that we want to round the number to.
     */
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
        }, [props.role, props.cost, itemsMap, props.items_status]
    )

    return (
        <div className={`bg-blue-900 h-full justify-items-center content-center ${businessUserBorder}`}>
            <div>Name: {props.username}</div>
            <div>Email: {props.email}</div>
            <div>Role: {props.role}</div>
            <div>Number of free items: {props.maximum_items}</div>
            <div>Number of items: {props.number_of_items}</div>
            <ProgressBar  items={itemsMap!} numberOfItemsOverall={props.number_of_items!} conditionName={"GOOD"} />
            <ProgressBar  items={itemsMap!} numberOfItemsOverall={props.number_of_items!} conditionName={"BAD"} />
            <ProgressBar  items={itemsMap!} numberOfItemsOverall={props.number_of_items!} conditionName={"BROKEN"}/>

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
                                            items_status: props.items_status,
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