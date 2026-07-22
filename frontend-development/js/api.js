const API_URL = "http://localhost:8080/api";

/* ==========================================================
   COMMON REQUEST FUNCTION
========================================================== */

async function request(endpoint, method = "GET", body = null) {

    try {

        const options = {
            method,
            headers: {
                "Content-Type": "application/json"
            }
        };

        if (body) {
            options.body = JSON.stringify(body);
        }

        const response = await fetch(API_URL + endpoint, options);

        let data = {};

        try {
            data = await response.json();
        } catch (e) {
            data = {};
        }

        if (!response.ok) {
            return {
                error: data.message || `Server Error (${response.status})`
            };
        }

        return data;

    } catch (error) {

        console.error("API Error:", error);

        return {
            error: "Unable to connect to the server. Make sure Spring Boot is running."
        };
    }

}

/* ==========================================================
   AUTH
========================================================== */

async function login(email, password) {
    return await request("/auth/login", "POST", {
        email,
        password
    });
}

async function registerResident(data) {
    return await request("/auth/register/resident", "POST", data);
}

async function registerWorker(data) {
    return await request("/auth/register/worker", "POST", data);
}

/* ==========================================================
   COMPLAINTS
========================================================== */

async function submitComplaint(data) {
    return await request("/complaints", "POST", data);
}

async function getAllComplaints() {

    const result = await request("/complaints");

    return Array.isArray(result) ? result : [];

}

async function getMyComplaints(residentId) {

    const result = await request(`/complaints/resident/${residentId}`);

    return Array.isArray(result) ? result : [];

}

async function getWorkerJobs(workerId) {

    const result = await request(`/complaints/worker/${workerId}`);

    return Array.isArray(result) ? result : [];

}

async function getComplaint(id) {
    return await request(`/complaints/${id}`);
}

async function assignWorker(complaintId, workerId, adminId) {

    return await request(
        `/complaints/${complaintId}/assign`,
        "PATCH",
        {
            workerId,
            adminId
        }
    );

}

async function updateStatus(complaintId, status, workerId, remarks) {

    return await request(
        `/complaints/${complaintId}/status`,
        "PATCH",
        {
            status,
            workerId,
            remarks
        }
    );

}

/* ==========================================================
   FEEDBACK
========================================================== */

async function submitFeedback(data) {
    return await request("/feedback", "POST", data);
}

async function getWorkerFeedback(workerId) {

    const result = await request(`/feedback/worker/${workerId}`);

    return Array.isArray(result) ? result : [];

}

/* ==========================================================
   NOTIFICATIONS
========================================================== */

async function getNotifications(userId) {

    const result = await request(`/notifications/${userId}`);

    return Array.isArray(result) ? result : [];

}

async function markAsRead(notificationId) {

    return await request(
        `/notifications/${notificationId}/read`,
        "PATCH"
    );

}

async function getUnreadCount(userId) {

    return await request(`/notifications/${userId}/unread`);

}

/* ==========================================================
   SESSION
========================================================== */

function saveUser(user) {

    sessionStorage.setItem(
        "user",
        JSON.stringify(user)
    );

}

function getUser() {

    const user = sessionStorage.getItem("user");

    return user ? JSON.parse(user) : null;

}

function logout() {

    sessionStorage.removeItem("user");

    window.location.href = "../pages/login.html";

}

function checkLogin() {

    const user = getUser();

    if (!user) {

        window.location.href = "../pages/login.html";
        return null;

    }

    return user;

}

function checkRole(role) {

    const user = checkLogin();

    if (!user) return null;

    if (user.role !== role) {

        alert("Access Denied");

        window.location.href = "../pages/login.html";

        return null;

    }

    return user;

}