import { useState } from "react";
import { api } from "../api";

export default function Users() {
  const [user, setUser] = useState({ name: "", email: "" });
  const [result, setResult] = useState("");
  const [error, setError] = useState("");

  const createUser = async () => {
    setError("");
    try {
      const res = await api.createUser(user);
      setResult(`User created with ID ${res.id}`);
    } catch (e) {
      setError("User service unavailable");
    }
  };

  return (
    <div>
      <h3>User Service</h3>
      <input placeholder="Name"
        onChange={(e) => setUser({ ...user, name: e.target.value })} />
      <input placeholder="Email"
        onChange={(e) => setUser({ ...user, email: e.target.value })} />
      <button onClick={createUser}>Create User</button>

      {result && <p>{result}</p>}
      {error && <p style={{ color: "red" }}>{error}</p>}
    </div>
  );
}
