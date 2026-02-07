import { useState } from "react";
import { api } from "../api";

export default function Orders() {
  const [order, setOrder] = useState({
    userId: "",
    product: "",
    quantity: 1
  });
  const [error, setError] = useState("");
  const [result, setResult] = useState("");

  const createOrder = async () => {
    setError("");
    try {
      const res = await api.createOrder(order);
      setResult(`Order created with ID ${res.id}`);
    } catch (e) {
      setError("Order failed (user-service may be down)");
    }
  };

  return (
    <div>
      <h3>Order Service</h3>
      <input placeholder="User ID"
        onChange={(e) => setOrder({ ...order, userId: e.target.value })} />
      <input placeholder="Product"
        onChange={(e) => setOrder({ ...order, product: e.target.value })} />
      <input type="number" placeholder="Qty"
        onChange={(e) => setOrder({ ...order, quantity: e.target.value })} />
      <button onClick={createOrder}>Create Order</button>

      {result && <p>{result}</p>}
      {error && <p style={{ color: "red" }}>{error}</p>}
    </div>
  );
}
