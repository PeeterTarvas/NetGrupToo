import React from "react";

interface ProgressBarInterface {
  items: Map<string,number>;
  numberOfItemsOverall: number;
  conditionName: string;
}

const ProgressBar:React.FC<ProgressBarInterface> = (props) => {

  const setBarStatus = () => {
    if (props.numberOfItemsOverall === 0) {
      return "0%"
    }
    return `${props.items.get(props.conditionName)! / props.numberOfItemsOverall * 100}%`
  }

  return (

        <div className="w-full h-4 mb-4 bg-gray-200 rounded-full dark:bg-gray-700">
          <div className={`h-4 bg-blue-600 rounded-full dark:bg-blue-500`} style={{width: setBarStatus()}}>{props.conditionName}</div>
        </div>

  )
}
export default ProgressBar;