import { useEffect, useState } from "react";
import { api } from "../api";

export default function Catalog() {
  const [items, setItems] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    api.getCatalog()
      .then(setItems)
      .catch(() => setError("Catalog service unavailable"));
  }, []);

  return (
    <div>
      <h3>Catalog Service</h3>
      {error && <p style={{ color: "red" }}>{error}</p>}
      <ul>
        {items.map(i => (
          <li key={i.id}>{i.name} - ₹{i.price}</li>
        ))}
      </ul>
    </div>
  );
}
