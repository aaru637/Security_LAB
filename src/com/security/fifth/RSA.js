let p = 0, q = 0, e = 0, d = 0, n = 0, residue = 0;

let gcd = (a, b) => {
    if (b === 0) return a;
    else return gcd(b, a % b);
}

let find_e = () => {
    for (e = 4; e < residue; e++) if (gcd(e, residue) === 1) break;
}

let find_d = () => {
    while (d < residue) {
        if (((e * d) % residue) === 1) break;
        d++;
    }
}

let mul = (encrypt, value, n) => {
    let result = [], i = 0;
    while (value > 3) {
        result[i++] = (Math.pow(encrypt, 4) % n);
        value -= 4;
    }
    if (value !== 0) result[i++] = (Math.pow(encrypt, value) % n);
    return (result.reduce((a, b) => a * b)) % n;
}

let convert = (arr, key, n) => {
    let temp = [];
    for (let i in arr) temp[i] = mul(arr.codePointAt(i), key, n);
    return temp;
}

let convertString = (arr) => {
    let result = "";
    for (let i in arr) result += String.fromCharCode(arr[i]);
    return result;
}

let form = document.getElementById("form");
form.addEventListener("submit", evaluate);
function evaluate(z) {
    z.preventDefault();
    // Choose p and q values.
    p = 17, q = 11;
    // Calculate n
    n = p * q;
    // Calculate residue
    residue = (p - 1) * (q - 1);
    // Choose the e value
    find_e();
    // Choose the d value
    find_d();

    let message = document.getElementById("message");
    let encrypt = convertString(convert(message.value, e, n));
    let decrypt = convertString(convert(encrypt, d, n));

    document.getElementById("first-prime").innerText = p;
    document.getElementById("second-prime").innerText = q;
    document.getElementById("n").innerText = n;
    document.getElementById("plain-text").innerText = message.value;
    document.getElementById("residue").innerText = residue;
    document.getElementById("public-key").innerText = e;
    document.getElementById("private-key").innerText = d;
    document.getElementById("encrypt").innerText = encrypt;
    document.getElementById("decrypt").innerText = decrypt;
    message.innerText = "";
}
