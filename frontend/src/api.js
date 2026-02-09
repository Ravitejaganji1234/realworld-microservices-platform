const BASE_URL = process.env.REACT_APP_API_BASE_URL;

async function request(path, options = {}) {
  try {
    const res = await fetch(`${BASE_URL}${path}`, {
      headers: { "Content-Type": "application/json" },
      ...options
    });

    if (!res.ok) {
      throw new Error(`HTTP ${res.status}`);
    }

    return res.json();
  } catch (err) {
    throw err;
  }
}

export const api = {
  createUser: (user) =>
    request("/api/users", {
      method: "POST",
      body: JSON.stringify(user)
    }),

  getUsers: (id) =>
    request(`/api/users/${id}`),

  createOrder: (order) =>
    request("/api/orders", {
      method: "POST",
      body: JSON.stringify(order)
    }),

  getOrders: (userId) =>
    request(`/api/orders/user/${userId}`),

  getCatalog: () =>
    request("/api/catalog")
};

// async function request(path, options = {}) {
//   const res = await fetch(path, {
//     headers: { "Content-Type": "application/json" },
//     ...options
//   });

//   if (!res.ok) {
//     throw new Error(`HTTP ${res.status}`);
//   }

//   return res.json();
// }

// export const api = {
//   createUser: (user) =>
//     request("/api/users", {
//       method: "POST",
//       body: JSON.stringify(user)
//     }),

//   getUsers: (id) =>
//     request(`/api/users/${id}`),

//   createOrder: (order) =>
//     request("/api/orders", {
//       method: "POST",
//       body: JSON.stringify(order)
//     }),

//   getOrders: (userId) =>
//     request(`/api/orders/user/${userId}`),

//   getCatalog: () =>
//     request("/api/catalog")
// };
