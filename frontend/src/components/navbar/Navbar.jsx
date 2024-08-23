import React from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuthCheck } from "../auth/useAuthCheck";
import axios from "axios";

const Navbar = () => {
  const { isAuthenticated, setIsAuthenticated } = useAuthCheck();
  const navigate = useNavigate();
  const hanldeLogout = async () => {
    try {
      const response = await axios.get("/api/user/logout");
      setIsAuthenticated(false);
      navigate("/login");
      console.log(response.data);
    } catch (err) {
      console.log(err);
    }
  };

  return (
    <nav className="px-5 z-50 py-[.8rem] bg-[#fbc02d] lg:px-20 flex justify-between">
      <div className="lg:mr-10 cursor-pointer flex items-center space-x-4">
        <li className="logo font-semibold text-slate-800 text-2xl">Food App</li>
      </div>

      <div className="flex items-center space-x-2 lg:space-x-10">
        <Link to={"/register"} className="text-slate-800 font-semibold">
          Register
        </Link>
        {isAuthenticated && (
          <Link to={"/profile"} className="text-slate-800 font-semibold">
            Profile
          </Link>
        )}
        {isAuthenticated ? (
          <button
            onClick={() => hanldeLogout()}
            className="text-slate-800 font-semibold"
          >
            Logout
          </button>
        ) : (
          <Link to={"/login"} className="text-slate-800 font-semibold">
            Login
          </Link>
        )}
      </div>
    </nav>
  );
};

export default Navbar;
