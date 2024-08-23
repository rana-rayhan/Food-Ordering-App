import { useState, useEffect } from "react";
import axios from "axios";

// Custom hook for authentication check
export const useAuthCheck = () => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [isAdmin, setAdmin] = useState(false);
  const [user, setUser] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    const checkAuth = async () => {
      try {
        const response = await axios.get("/auth/check");
        setIsAuthenticated(true);

        setUser(response?.data);
        if (response.data.data.role === "ROLE_ADMIN") {
          setAdmin(true);
        }
      } catch (err) {
        setError(err);
      }
    };

    checkAuth();
  }, []);

  return { isAuthenticated, setIsAuthenticated, user, isAdmin, error };
};
