import { Routes, Route } from 'react-router-dom';
import HomePage from "./Pages/HomePage";
import RegisterPage from "./Pages/RegisterPage";
import MainPage from "./Pages/MainPage";

const Main = () => {

    return (
        <Routes >
            <Route path={"/*"}>
             <Route index element={<HomePage/>} />
             <Route path='register' element={<RegisterPage/>} />
             <Route path={'main'} element={<MainPage/>} />
            </Route>
        </Routes>
);
}
export default Main;