import Users from "./components/Users";
import Orders from "./components/Orders";
import Catalog from "./components/Catalog";

export default function App() {
  return (
    <div style={{ padding: "20px" }}>
      <h2>K8s Real-World Practice App - Ravi</h2>

      <Users />
      <hr />
      <Orders />
      <hr />
      <Catalog />
    </div>
  );
}
