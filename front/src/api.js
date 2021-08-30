import axios from "axios";
const mock = true;
export const login = async (params) => {
  let { data } = await axios.post("/auth/authenticate", { ...params });
  if (mock)
    data = {
      jwtToken:
        "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhc2VsbHkxIiwiZXhwIjoxNjI3OTM1MDE4LCJpYXQiOjE2Mjc5MTcwMTh9.cF4zAQndXr8p6S4KND35uYjFoTT4yknpOZ72E88t3t6BwPinpGYiburc-3077aCmSiv83OXgGcpm5qnL-7pigA",
    };
  return data;
};

export const fetchHomepageRegistered = async () => {
  let { data } = await axios.get("/feed/feed/registered");
  return data;
};
export const fetchHomepageUnregistered = async () => {
  let { data } = await axios.get("/feed/feed/public/anonymous");
  if (mock)
    data = {
      posts: [
        {
          postID: 6204,
          userID: "kmiroy2",
          numOfLikes: 441,
          numOfComments: 437,
          published: "2021-08-02T00:00:00",
          location: "Jovana Subotica 16, Novi Sad",
          seeable: true,
          postType: "post",
          url: ["magpie.mp4"],
          description: "garfield @seginton9 #cats #pets #fat",
          hashtags: ["pets", "cats", "fat"],
          profiletags: ["seginton9"],
          liked: ["fcremins3"],
          disliked: [],
        },
        {
          postID: 3784,
          userID: "fcremins3",
          numOfLikes: 439,
          numOfComments: 86,
          published: "2021-08-02T00:00:00",
          location: "Kraljevica Marka 17, Novi Sad",
          seeable: true,
          postType: "post",
          url: ["adler.mp4", "redeyes.jpg"],
          description: "good vibes #smile #summer #girlsday @mrubinov0",
          hashtags: ["girlsday", "summer", "smile"],
          profiletags: ["mrubinov0"],
          liked: ["kmiroy2", "gdybbe8", "aselly1"],
          disliked: [],
        },
        {
          postID: 7400,
          userID: "kmiroy2",
          numOfLikes: 72,
          numOfComments: 180,
          published: "2021-08-02T00:00:00",
          location: "Viseslava Bugarinovica 87, Sevojno",
          seeable: true,
          postType: "post",
          url: ["bullfinch.mp4"],
          description: "happy thoughts #happy #smile #love",
          hashtags: ["love", "happy", "smile"],
          profiletags: [],
          liked: [],
          disliked: [],
        },
        {
          postID: 4260,
          userID: "fcremins3",
          numOfLikes: 335,
          numOfComments: 178,
          published: "2021-08-02T00:00:00",
          location: "Rudnik",
          seeable: true,
          postType: "post",
          url: ["redy.mp4"],
          description: "fast cars #fast #luxury",
          hashtags: ["fast", "luxury"],
          profiletags: [],
          liked: [],
          disliked: ["aselly1"],
        },
        {
          postID: 8548,
          userID: "kmiroy2",
          numOfLikes: 216,
          numOfComments: 219,
          published: "2021-08-02T00:00:00",
          location: "Bore Stankovica 7, Vranje",
          seeable: true,
          postType: "post",
          url: ["meadow.mp4"],
          description: "aristoCATS #cats #pets #hope",
          hashtags: ["pets", "cats", "hope"],
          profiletags: [],
          liked: [],
          disliked: [],
        },
        {
          postID: 9854,
          userID: "kmiroy2",
          numOfLikes: 213,
          numOfComments: 453,
          published: "2021-08-02T00:00:00",
          location: "Iva Andrica 1, Novi Sad",
          seeable: true,
          postType: "album",
          url: ["butterfly.jpg", "soko.mp4", "titmouse.jpeg"],
          description: "live your life #free #smile #life @fcremins3",
          hashtags: ["free", "life", "smile"],
          profiletags: ["fcremins3"],
          liked: [],
          disliked: [],
        },
        {
          postID: 2721,
          userID: "fcremins3",
          numOfLikes: 227,
          numOfComments: 283,
          published: "2021-08-02T00:00:00",
          location: "Zivka Davidovica 83, Beograd",
          seeable: true,
          postType: "album",
          url: ["flying.mp4", "skyler.jpg"],
          description: "tiktok famous #dream #smile #job",
          hashtags: ["dream", "job", "smile"],
          profiletags: [],
          liked: [],
          disliked: [],
        },
        {
          postID: 1981,
          userID: "fcremins3",
          numOfLikes: 414,
          numOfComments: 56,
          published: "2021-08-02T00:00:00",
          location: "Desanke Maksimovic 82, Kragujevac",
          seeable: true,
          postType: "post",
          url: ["rainlake.mp4"],
          description: "best friends #friends #love #hope",
          hashtags: ["love", "hope", "friends"],
          profiletags: [],
          liked: [],
          disliked: [],
        },
        {
          postID: 778,
          userID: "gdybbe8",
          numOfLikes: 47,
          numOfComments: 195,
          published: "2021-08-02T00:00:00",
          location: "Kralja Petra I 45, Novi Sad",
          seeable: true,
          postType: "post",
          url: ["crvendac.jpg"],
          description: "happy birthday! #birthday #love",
          hashtags: ["birthday", "love"],
          profiletags: [],
          liked: ["fcremins3", "mkellick4", "aselly1"],
          disliked: [],
        },
        {
          postID: 2383,
          userID: "fcremins3",
          numOfLikes: 191,
          numOfComments: 373,
          published: "2021-08-02T00:00:00",
          location: "Proleterske brigade 9, Uzice",
          seeable: true,
          postType: "post",
          url: ["feed.jpg"],
          description: "@gdybbe8 #nature",
          hashtags: ["nature"],
          profiletags: ["gdybbe8"],
          liked: [],
          disliked: [],
        },
      ],
      stories: [
        {
          postID: 8267,
          userID: "kmiroy2",
          numOfLikes: 0,
          numOfComments: 0,
          published: "2021-08-02T00:00:00",
          location: "",
          seeable: true,
          postType: "story",
          url: ["redol.jpg"],
          description: "",
          hashtags: [],
          profiletags: [],
          liked: [],
          disliked: [],
        },
        {
          postID: 8267,
          userID: "kmiroy2",
          numOfLikes: 0,
          numOfComments: 0,
          published: "2021-08-02T00:00:00",
          location: "",
          seeable: true,
          postType: "story",
          url: ["redol.jpg"],
          description: "",
          hashtags: [],
          profiletags: [],
          liked: [],
          disliked: [],
        },
        {
          postID: 8267,
          userID: "kmiroy2",
          numOfLikes: 0,
          numOfComments: 0,
          published: "2021-08-02T00:00:00",
          location: "",
          seeable: true,
          postType: "story",
          url: ["redol.jpg"],
          description: "",
          hashtags: [],
          profiletags: [],
          liked: [],
          disliked: [],
        },
        {
          postID: 8267,
          userID: "kmiroy2",
          numOfLikes: 0,
          numOfComments: 0,
          published: "2021-08-02T00:00:00",
          location: "",
          seeable: true,
          postType: "story",
          url: ["redol.jpg"],
          description: "",
          hashtags: [],
          profiletags: [],
          liked: [],
          disliked: [],
        },
        {
          postID: 8267,
          userID: "kmiroy2",
          numOfLikes: 0,
          numOfComments: 0,
          published: "2021-08-02T00:00:00",
          location: "",
          seeable: true,
          postType: "story",
          url: ["redol.jpg"],
          description: "",
          hashtags: [],
          profiletags: [],
          liked: [],
          disliked: [],
        },
        {
          postID: 8267,
          userID: "kmiroy2",
          numOfLikes: 0,
          numOfComments: 0,
          published: "2021-08-02T00:00:00",
          location: "",
          seeable: true,
          postType: "story",
          url: ["redol.jpg"],
          description: "",
          hashtags: [],
          profiletags: [],
          liked: [],
          disliked: [],
        },
        {
          postID: 8267,
          userID: "kmiroy2",
          numOfLikes: 0,
          numOfComments: 0,
          published: "2021-08-02T00:00:00",
          location: "",
          seeable: true,
          postType: "story",
          url: ["redol.jpg"],
          description: "",
          hashtags: [],
          profiletags: [],
          liked: [],
          disliked: [],
        },
        {
          postID: 8267,
          userID: "kmiroy2",
          numOfLikes: 0,
          numOfComments: 0,
          published: "2021-08-02T00:00:00",
          location: "",
          seeable: true,
          postType: "story",
          url: ["redol.jpg"],
          description: "",
          hashtags: [],
          profiletags: [],
          liked: [],
          disliked: [],
        },
      ],
    };

  return data;
};

export const searchByNameRegistered = async (param) => {
  let { data } = await axios.get(`/user/search/searchProfiles/${param}`);
  return data;
};
export const searchByNamePublic = async (param) => {
  let { data } = await axios.get(`/user/search/public/searchProfiles/${param}`);
  if (mock)
    data = [
      {
        userId: 7,
        username: "fcremins3",
        name: "Franky",
        surname: "Cremins",
        privacy: false,
      },
      {
        userId: 19,
        username: "kmiroy2",
        name: "Kort",
        surname: "Miroy",
        privacy: false,
      },
      {
        userId: 20,
        username: "mrubinov0",
        name: "Lola",
        surname: "OhLa",
        privacy: false,
      },
      {
        userId: 64,
        username: "mkellick4",
        name: "Minerva",
        surname: "Kellick",
        privacy: true,
      },
      {
        userId: 68,
        username: "shundell5",
        name: "Suki",
        surname: "Hundell",
        privacy: true,
      },
      {
        userId: 73,
        username: "aselly1",
        name: "Allan",
        surname: "Selly",
        privacy: true,
      },
      {
        userId: 86,
        username: "vwitherup6",
        name: "Verina",
        surname: "Witherup",
        privacy: true,
      },
      {
        userId: 98,
        username: "fmatterface7",
        name: "Finley",
        surname: "Matterface",
        privacy: true,
      },
    ];
  return data;
};

export const searchByUsernameRegistered = async (param) => {
  let { data } = await axios.get(`/user/search/searchHash/${param}`);
  return data;
};
export const searchByUsernamePublic = async (param) => {
  let { data } = await axios.get(`/user/search/public/searchHash/${param}`);
  return data;
};

export const searchByLocationRegistered = async (param) => {
  let { data } = await axios.get(`/feed/search/searchLocation/${param}`);
  return data;
};
export const searchByLocationPublic = async (param) => {
  let { data } = await axios.get(`/feed/search/public/searchLocation/${param}`);
  return data;
};

export const searchByHashtagRegistered = async (param) => {
  let { data } = await axios.get(`/feed/search/searchHash/${param}`);
  return data;
};
export const searchByHashtagPublic = async (param) => {
  let { data } = await axios.get(`/feed/search/public/searchHash/${param}`);
  if (mock)
    data = [
      {
        postID: 778,
        userID: "gdybbe8",
        numOfLikes: 47,
        numOfComments: 195,
        published: "2021-08-02T00:00:00",
        location: "Kralja Petra I 45, Novi Sad",
        seeable: true,
        postType: "post",
        url: ["crvendac.jpg"],
        description: "happy birthday! #birthday #love",
        hashtags: ["birthday", "love"],
        profiletags: [],
        liked: ["fcremins3", "mkellick4", "aselly1"],
        disliked: [],
      },
      {
        postID: 841,
        userID: "kmiroy2",
        numOfLikes: 220,
        numOfComments: 113,
        published: "2021-08-02T00:00:00",
        location: "Milosa Bozanovica 18, Uzice",
        seeable: true,
        postType: "post",
        url: ["bluebird.jpg"],
        description: "smile more #smile #goodday #love",
        hashtags: ["love", "goodday", "smile"],
        profiletags: [],
        liked: [],
        disliked: [],
      },
      {
        postID: 2383,
        userID: "fcremins3",
        numOfLikes: 191,
        numOfComments: 373,
        published: "2021-08-02T00:00:00",
        location: "Proleterske brigade 9, Uzice",
        seeable: true,
        postType: "post",
        url: ["feed.jpg"],
        description: "@gdybbe8 #nature",
        hashtags: ["nature"],
        profiletags: ["gdybbe8"],
        liked: [],
        disliked: [],
      },
      {
        postID: 2632,
        userID: "gdybbe8",
        numOfLikes: 137,
        numOfComments: 181,
        published: "2021-08-02T00:00:00",
        location: "Juzni bulevar 1, Beograd",
        seeable: true,
        postType: "post",
        url: ["sea.mp4"],
        description: "illusion #dream #smile #hope",
        hashtags: ["dream", "hope", "smile"],
        profiletags: [],
        liked: [],
        disliked: ["aselly1"],
      },
      {
        postID: 2721,
        userID: "fcremins3",
        numOfLikes: 227,
        numOfComments: 283,
        published: "2021-08-02T00:00:00",
        location: "Zivka Davidovica 83, Beograd",
        seeable: true,
        postType: "album",
        url: ["flying.mp4", "skyler.jpg"],
        description: "tiktok famous #dream #smile #job",
        hashtags: ["dream", "job", "smile"],
        profiletags: [],
        liked: [],
        disliked: [],
      },
      {
        postID: 3784,
        userID: "fcremins3",
        numOfLikes: 439,
        numOfComments: 86,
        published: "2021-08-02T00:00:00",
        location: "Kraljevica Marka 17, Novi Sad",
        seeable: true,
        postType: "post",
        url: ["adler.mp4", "redeyes.jpg"],
        description: "good vibes #smile #summer #girlsday @mrubinov0",
        hashtags: ["girlsday", "summer", "smile"],
        profiletags: ["mrubinov0"],
        liked: ["kmiroy2", "gdybbe8", "aselly1"],
        disliked: [],
      },
      {
        postID: 4260,
        userID: "fcremins3",
        numOfLikes: 335,
        numOfComments: 178,
        published: "2021-08-02T00:00:00",
        location: "Rudnik",
        seeable: true,
        postType: "post",
        url: ["redy.mp4"],
        description: "fast cars #fast #luxury",
        hashtags: ["fast", "luxury"],
        profiletags: [],
        liked: [],
        disliked: ["aselly1"],
      },
      {
        postID: 6204,
        userID: "kmiroy2",
        numOfLikes: 441,
        numOfComments: 437,
        published: "2021-08-02T00:00:00",
        location: "Jovana Subotica 16, Novi Sad",
        seeable: true,
        postType: "post",
        url: ["magpie.mp4"],
        description: "garfield @seginton9 #cats #pets #fat",
        hashtags: ["pets", "cats", "fat"],
        profiletags: ["seginton9"],
        liked: ["fcremins3"],
        disliked: [],
      },
      {
        postID: 7400,
        userID: "kmiroy2",
        numOfLikes: 72,
        numOfComments: 180,
        published: "2021-08-02T00:00:00",
        location: "Viseslava Bugarinovica 87, Sevojno",
        seeable: true,
        postType: "post",
        url: ["bullfinch.mp4"],
        description: "happy thoughts #happy #smile #love",
        hashtags: ["love", "happy", "smile"],
        profiletags: [],
        liked: [],
        disliked: [],
      },
      {
        postID: 8548,
        userID: "kmiroy2",
        numOfLikes: 216,
        numOfComments: 219,
        published: "2021-08-02T00:00:00",
        location: "Bore Stankovica 7, Vranje",
        seeable: true,
        postType: "post",
        url: ["meadow.mp4"],
        description: "aristoCATS #cats #pets #hope",
        hashtags: ["pets", "cats", "hope"],
        profiletags: [],
        liked: [],
        disliked: [],
      },
    ];
  return data;
};

export const searchByProfileHashRegistered = async (param) => {
  let { data } = await axios.get(`/feed/search/searchProfileHash/${param}`);
  return data;
};
export const searchByProfileHashPublic = async (param) => {
  let { data } = await axios.get(
    `/feed/search/public/searchProfileHash/${param}`
  );
  return data;
};

export const getMyProfileContent = async () => {
  let { data } = await axios.get(`/feed/posts/myProfile`);
  if (mock)
    data = {
      posts: [
        {
          postID: 841,
          userID: "kmiroy2",
          numOfLikes: 220,
          numOfComments: 113,
          published: "2021-08-02T00:00:00",
          location: "Milosa Bozanovica 18, Uzice",
          seeable: true,
          postType: "post",
          url: ["bluebird.jpg"],
          description: "smile more #smile #goodday #love",
          hashtags: ["love", "goodday", "smile"],
          profiletags: [],
          liked: [],
          disliked: [],
        },
        {
          postID: 6204,
          userID: "kmiroy2",
          numOfLikes: 441,
          numOfComments: 437,
          published: "2021-08-02T00:00:00",
          location: "Jovana Subotica 16, Novi Sad",
          seeable: true,
          postType: "post",
          url: ["magpie.mp4"],
          description: "garfield @seginton9 #cats #pets #fat",
          hashtags: ["pets", "cats", "fat"],
          profiletags: ["seginton9"],
          liked: ["fcremins3"],
          disliked: [],
        },
        {
          postID: 7400,
          userID: "kmiroy2",
          numOfLikes: 72,
          numOfComments: 180,
          published: "2021-08-02T00:00:00",
          location: "Viseslava Bugarinovica 87, Sevojno",
          seeable: true,
          postType: "post",
          url: ["bullfinch.mp4"],
          description: "happy thoughts #happy #smile #love",
          hashtags: ["love", "happy", "smile"],
          profiletags: [],
          liked: [],
          disliked: [],
        },
        {
          postID: 8548,
          userID: "kmiroy2",
          numOfLikes: 216,
          numOfComments: 219,
          published: "2021-08-02T00:00:00",
          location: "Bore Stankovica 7, Vranje",
          seeable: true,
          postType: "post",
          url: ["meadow.mp4"],
          description: "aristoCATS #cats #pets #hope",
          hashtags: ["pets", "cats", "hope"],
          profiletags: [],
          liked: [],
          disliked: [],
        },
        {
          postID: 9854,
          userID: "kmiroy2",
          numOfLikes: 213,
          numOfComments: 453,
          published: "2021-08-02T00:00:00",
          location: "Iva Andrica 1, Novi Sad",
          seeable: true,
          postType: "album",
          url: ["butterfly.jpg", "soko.mp4", "titmouse.jpeg"],
          description: "live your life #free #smile #life @fcremins3",
          hashtags: ["free", "life", "smile"],
          profiletags: ["fcremins3"],
          liked: [],
          disliked: [],
        },
      ],
      stories: [
        {
          postID: 8267,
          userID: "kmiroy2",
          numOfLikes: 0,
          numOfComments: 0,
          published: "2021-08-02T00:00:00",
          location: "",
          seeable: true,
          postType: "story",
          url: ["redol.jpg"],
          description: "",
          hashtags: [],
          profiletags: [],
          liked: [],
          disliked: [],
        },
      ],
    };
  return data;
};

export const getprofileContentRegistered = async (username) => {
  let { data } = await axios.get(`/feed/posts/userProfile/${username}`);
  return data;
};
export const getprofileContentPublic = async (username) => {
  let { data } = await axios.get(`/feed/posts/public/userProfile/${username}`);
  if (mock)
    data = {
      posts: [
        {
          postID: 1981,
          userID: "fcremins3",
          numOfLikes: 414,
          numOfComments: 56,
          published: "2021-08-02T00:00:00",
          location: "Desanke Maksimovic 82, Kragujevac",
          seeable: true,
          postType: "post",
          url: ["rainlake.mp4"],
          description: "best friends #friends #love #hope",
          hashtags: ["love", "hope", "friends"],
          profiletags: [],
          liked: [],
          disliked: [],
        },
        {
          postID: 2383,
          userID: "fcremins3",
          numOfLikes: 191,
          numOfComments: 373,
          published: "2021-08-02T00:00:00",
          location: "Proleterske brigade 9, Uzice",
          seeable: true,
          postType: "post",
          url: ["feed.jpg"],
          description: "@gdybbe8 #nature",
          hashtags: ["nature"],
          profiletags: ["gdybbe8"],
          liked: [],
          disliked: [],
        },
        {
          postID: 2721,
          userID: "fcremins3",
          numOfLikes: 227,
          numOfComments: 283,
          published: "2021-08-02T00:00:00",
          location: "Zivka Davidovica 83, Beograd",
          seeable: true,
          postType: "album",
          url: ["flying.mp4", "skyler.jpg"],
          description: "tiktok famous #dream #smile #job",
          hashtags: ["dream", "job", "smile"],
          profiletags: [],
          liked: [],
          disliked: [],
        },
        {
          postID: 3784,
          userID: "fcremins3",
          numOfLikes: 439,
          numOfComments: 86,
          published: "2021-08-02T00:00:00",
          location: "Kraljevica Marka 17, Novi Sad",
          seeable: true,
          postType: "post",
          url: ["adler.mp4", "redeyes.jpg"],
          description: "good vibes #smile #summer #girlsday @mrubinov0",
          hashtags: ["girlsday", "summer", "smile"],
          profiletags: ["mrubinov0"],
          liked: ["kmiroy2", "gdybbe8", "aselly1"],
          disliked: [],
        },
        {
          postID: 4260,
          userID: "fcremins3",
          numOfLikes: 335,
          numOfComments: 178,
          published: "2021-08-02T00:00:00",
          location: "Rudnik",
          seeable: true,
          postType: "post",
          url: ["redy.mp4"],
          description: "fast cars #fast #luxury",
          hashtags: ["fast", "luxury"],
          profiletags: [],
          liked: [],
          disliked: ["aselly1"],
        },
      ],
      stories: [],
    };
  return data;
};

export const getFollowersForUser = async (username) => {
  let { data } = await axios.get(`/user/follows/public/followers/${username}`);
  //   if (!data) alert('No such user');
  if (mock) data = ["gdybbe8", "mrubinov0", "kmiroy2"];
  return data;
};

export const getProfileInfoUserRegistered = async (username) => {
  let { data } = await axios.get(`/user/users/${username}`);
  return data;
};

export const getProfileInfoUserPublic = async (username) => {
  let { data } = await axios.get(`/user/users/public/${username}`);
  return data;
};

export const getProfileInfoMe = async () => {
  let { data } = await axios.get(`/user/users/me`);
  data = {
    userId: 73,
    username: "aselly1",
    name: "Allan",
    surname: "Selly",
    email: "aselly1@wikispaces.com",
    phoneNumber: "(201) 4367121",
    sex: true,
    birthday: "2005-02-26T19:33:22.000+00:00",
    website: "ante ipsum primis",
    bio:
      "Mauris enim leo, rhoncus sed, vestibulum sit amet, cursus id, turpis. Integer aliquet, massa id lobortis convallis, tortor risus dapibus augue, vel accumsan tellus nisi eu orci. Mauris lacinia sapien quis libero.",
    privacy: true,
  };
  return data;
};

export const updateInfo = async (params) => {
  let { data } = await axios.put(`/user/users/update`, { ...params });
  return data;
};
