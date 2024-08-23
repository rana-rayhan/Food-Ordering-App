import React from "react";
import { useAuthCheck } from "../components/auth/useAuthCheck";
import { Link } from "react-router-dom";

const Profile = () => {
  const { user, isAuthenticated, error } = useAuthCheck();

  if (isAuthenticated === false) {
    return (
      <div className="flex justify-center items-center h-screen bg-gray-100">
        <div className="text-center">
          <h1 className="text-6xl font-bold text-red-600 mb-4">
            403: Access Denied
          </h1>
          {/* Render error.message if error is an object, otherwise just render error */}
          <p className="text-lg text-gray-600 mb-8">
            {error?.message || JSON.stringify(error)}
          </p>
          <Link to="/login" className="text-blue-500 hover:underline">
            Go to Login
          </Link>
        </div>
      </div>
    );
  }

  return (
    <div className="flex justify-center items-center h-screen bg-gray-100">
      <div className="text-center">
        <h1 className="text-6xl font-bold text-red-600 mb-4">
          Welcome: {user?.data?.fullName}
        </h1>
      </div>
    </div>
  );
};

export default Profile;
