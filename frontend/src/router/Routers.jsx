import React from "react";
import { Route, Routes } from "react-router-dom";
import Login from "../pages/Login";
import Home from "../pages/Home";
import Error from "../pages/Error";
import Register from "../pages/Register";
import Profile from "../pages/Profile";

const Router = () => {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route path="/profile" element={<Profile />} />

      <Route path="*" element={<Error />} />
    </Routes>
  );
};

export default Router;
