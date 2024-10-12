package com.example.healthifymobile.modelo;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.healthifymobile.listener.CartListener;
import com.example.healthifymobile.listener.CartPayListener;
import com.example.healthifymobile.listener.EmentaListener;
import com.example.healthifymobile.listener.LoginListener;
import com.example.healthifymobile.listener.MetodosPagamentoListener;
import com.example.healthifymobile.listener.NewAccountListener;
import com.example.healthifymobile.listener.PedidosListener;
import com.example.healthifymobile.listener.PerfilListener;
import com.example.healthifymobile.listener.RefeicoesListener;
import com.example.healthifymobile.listener.RelogioPontoListener;
import com.example.healthifymobile.listener.ReviewListener;
import com.example.healthifymobile.listener.TableListener;
import com.example.healthifymobile.utils.AccountRegistJsonParser;
import com.example.healthifymobile.utils.CartJsonParser;
import com.example.healthifymobile.utils.HealthifyJsonParser;
import com.example.healthifymobile.utils.MealsJsonParser;
import com.example.healthifymobile.utils.ReviewJsonParser;
import com.example.healthifymobile.utils.StaffOperationsParser;
import com.example.healthifymobile.utils.TableReserveParser;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SingletonGestorBd {
    private static SingletonGestorBd instance = null;

    private ArrayList<Refeicao> refeicaoArrayList;
    private ArrayList<Review> reviewArrayList;
    private ArrayList<CartaoPagamento> cartaoPagamentoArrayList;
    private healthifyBdHelper helper = null;

    private static RequestQueue volleyQueue = null;



    private static final String mUrlAPI = "http://localhost/healthify/backend/web/api/";

    private static final String action_login_backend = "first/login";

    private static final String action_newUser = "first/register";
    private static final String action_Profile = "profile";
    private static final String action_meals = "meal/";
    private static final String action_Cart = "cart";
    private static final String action_GetCart = "customcart/fromuser/";
    private static final String action_GetHistorico = "sales/sold/";
    private static final String action_Review = "review";
    private static final String action_GetReview = "review/fromuser/";
    private static final String action_Payment = "payment/";
    private static final String pay = "/pay/";
    private static final String cash = "cash/";
    private static final String action_workedtime = "workedtime/attendance/";
    private static final String action_ocuparmesa = "tables/ocupar/";
    private static final String action_libertarmesa = "tables/libertar/";
    private static final String action_reservarmesa = "tables/reservar/";

    private LoginListener loginListener;
    private NewAccountListener newAccountListener;
    private PerfilListener perfilListener;
    private PedidosListener pedidosListener;
    private EmentaListener ementaListener;
    private CartListener cartListener;
    private RefeicoesListener refeicoesListener;
    private ReviewListener reviewListener;
    private MetodosPagamentoListener pagamentoListener;
    private CartPayListener cartPayListener;
    private RelogioPontoListener relogioPontoListener;
    private TableListener tableListener;


    //construtor singleton
    public SingletonGestorBd(Context context) {
        refeicaoArrayList = new ArrayList<>();
        reviewArrayList = new ArrayList<>();
        helper = new healthifyBdHelper(context);
    }

    public static synchronized SingletonGestorBd getInstance(Context context) {
        if (instance == null) {
            instance = new SingletonGestorBd(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instance;
    }

    //region listeners
    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    public void setNewAccontListener(NewAccountListener newAccountListener) {
        this.newAccountListener = newAccountListener;
    }

    public void setPerfilListener(PerfilListener perfilListener) {
        this.perfilListener = perfilListener;
    }

    public void setEmentaListener(EmentaListener ementaListener) {
        this.ementaListener = ementaListener;
    }
    public void setPedidosListener(PedidosListener pedidosListener) {
        this.pedidosListener = pedidosListener;
    }

    public void setCartListener(CartListener cartListener) {
        this.cartListener = cartListener;
    }

    public void setRefeicaoListener(RefeicoesListener refeicoesListener) {
        this.refeicoesListener = refeicoesListener;
    }

    public void setReviewListener(ReviewListener reviewListener) {
        this.reviewListener = reviewListener;
    }

    public void setMetodoPagamentoListener(MetodosPagamentoListener pagamentoListener) {
        this.pagamentoListener = pagamentoListener;
    }

    public void setCartPayListener(CartPayListener cartPayListener) {
        this.cartPayListener = cartPayListener;
    }

    public void setRelogioPontoListener(RelogioPontoListener relogioPontoListener) {
        this.relogioPontoListener = relogioPontoListener;
    }
    public void setTableListener(TableListener tableListener) {
        this.tableListener = tableListener;
    }

    //endregion
    //region metodosBd
    public void adicionarRefeicoesBD(ArrayList<Refeicao> refeicoes) {
        helper.removerRefeicoes();
        for (Refeicao r : refeicoes)
            helper.adicionarRefeicao(r);
    }

    public ArrayList<Refeicao> getRefeicoesBD() {
        refeicaoArrayList = helper.carregarRefeicoes();
        return refeicaoArrayList;
    }

    public CartaoPagamento getCartaoBD(int id) {
        for (CartaoPagamento l : cartaoPagamentoArrayList)
            if (l.getId() == id)
                return l;
        return null;
    }

    public void adicionarReviewsBD(ArrayList<Review> reviews) {
        helper.removerReviews();
        for (Review r : reviews)
            helper.adicionarReview(r);
    }

    public ArrayList<Review> getReviewsBD() {
        reviewArrayList = helper.carregarReviews();
        return reviewArrayList;
    }

    //endregion
    //region Login
    public void loginAPI(final String email, final String password, final Context context) {
        if (!HealthifyJsonParser.isConnectionInternet(context))
            Toast.makeText(context, "Sem Ligaçao a internet", Toast.LENGTH_SHORT).show();
        else if (HealthifyJsonParser.isConnectionInternet(context)) {
            StringRequest req = new StringRequest(Request.Method.POST, mUrlAPI + action_login_backend, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    InfoPerfil perfil = HealthifyJsonParser.parserJsonLogin(response);

                    if (loginListener != null) {
                        loginListener.onValidateLogin(perfil, context);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error.getMessage());
                    //Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    params.put("email", email);
                    params.put("password", password);
                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }

    //endregion
    //region Register
    public void newAccountAPI(String username, String email, String password, final Context context) {
        if (!HealthifyJsonParser.isConnectionInternet(context))
            Toast.makeText(context, "Sem Ligação a internet", Toast.LENGTH_SHORT).show();
        else if (HealthifyJsonParser.isConnectionInternet(context)) {
            StringRequest req = new StringRequest(Request.Method.POST, mUrlAPI + action_newUser, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    int userid = AccountRegistJsonParser.parserJsonRegister(response);

                    if (newAccountListener != null) {
                        newAccountListener.onValidateRegister(userid, username, email, password);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error.getMessage());
                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    params.put("username", username);
                    params.put("email", email);
                    params.put("password", password);
                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }

    public void newProfileAPI(int nif, String nome, int telemovel, String rua, int porta, String andar, String cidade, int userId, final Context context) {
        StringRequest req = new StringRequest(Request.Method.POST, mUrlAPI + action_Profile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                boolean message = false;
                if (response.length() > 0)
                    message = true;

                if (newAccountListener != null) {
                    newAccountListener.onValidateNewProfile(message);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
                //Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("nif", nif + "");
                params.put("name", nome);
                params.put("cellphone", telemovel + "");
                params.put("street", rua);
                params.put("door", porta + "");
                params.put("floor", andar);
                params.put("city", cidade);
                params.put("userid", userId + "");
                return params;
            }
        };
        volleyQueue.add(req);
    }

    public void updateProfileAPI(int nif, String nome, int telemovel, String rua, int porta, String andar, String cidade, final Context context, int profileid, String email, String token) {
        if (!HealthifyJsonParser.isConnectionInternet(context))
            Toast.makeText(context, "Sem Ligaçao a internet", Toast.LENGTH_SHORT).show();
        else {
            StringRequest req = new StringRequest(Request.Method.PUT, mUrlAPI + action_Profile + "/" + profileid, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    InfoPerfil perfil = HealthifyJsonParser.parserPerfil(response);

                    if (perfilListener != null) {
                        perfilListener.onUpdatePerfil(perfil);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    params.put("nif", nif + "");
                    params.put("name", nome + "");
                    params.put("cellphone", telemovel + "");
                    params.put("street", rua + "");
                    params.put("door", porta + "");
                    params.put("floor", andar + "");
                    params.put("city", cidade + "");
                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }


    //endregion
    //region Pedidos
    public void showEmentaAPI(final Context context) {
        if (!HealthifyJsonParser.isConnectionInternet(context))
            Toast.makeText(context, "Sem Ligaçao a internet", Toast.LENGTH_LONG).show();
        else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPI + action_meals, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    ArrayList<MealsAPI> mealsAPI = MealsJsonParser.parserMeal(response);

                    if (ementaListener != null) {
                        ementaListener.onLoadEmenta(mealsAPI);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }
    }

    public void getEmentaAPI(final Context context, final String tableid) {
        if (!HealthifyJsonParser.isConnectionInternet(context))
            Toast.makeText(context, "Sem Ligaçao a internet", Toast.LENGTH_LONG).show();
        else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPI + action_meals, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    ArrayList<MealsAPI> mealsAPI = MealsJsonParser.parserMeal(response);

                    if (pedidosListener != null) {
                        pedidosListener.onRefreshListapedidos(mealsAPI, tableid);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }
    }

    //endregion
    //region Cart
    //Obter todos os elementos do carrinho
    public void getCartApiAll(int userProfileId, final Context context) {
        if (!HealthifyJsonParser.isConnectionInternet(context))
            Toast.makeText(context, "Sem Ligaçao a internet", Toast.LENGTH_LONG).show();
        else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPI + action_GetCart + userProfileId, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    ArrayList<CartAPI> cartArray = CartJsonParser.parserGetCartAll(response);
                    if (cartListener != null) {
                        cartListener.onRefreshCart(cartArray);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    System.out.println(error.getMessage());
                }
            });
            volleyQueue.add(req);
        }
    }

    //adicionar ao carrinho
    public void addCart(final int userprofilesid, final int mealsid, final String sellingprice, final int itemquantity, final Context context, final String tableid) {
        if (!HealthifyJsonParser.isConnectionInternet(context))
            Toast.makeText(context, "Sem Ligaçao a internet", Toast.LENGTH_SHORT).show();
        else {
            StringRequest req = new StringRequest(Request.Method.POST, mUrlAPI + action_Cart, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    CartJsonParser.parserCart(response);

                    getCartApiAll(userprofilesid, context);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    System.out.println(error.getMessage());
                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    params.put("userprofilesid", userprofilesid + "");
                    params.put("mealsid", mealsid + "");
                    params.put("sellingprice", sellingprice);
                    params.put("itemquantity", itemquantity + "");
                    params.put("mesa", tableid + "");
                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }

    public void cartPutRequest(int cartItemId, int itemquantity, int userprofilesid, Context context) {
        if (!HealthifyJsonParser.isConnectionInternet(context))
            Toast.makeText(context, "Sem Ligaçao a internet", Toast.LENGTH_SHORT).show();
        else {
            StringRequest req = new StringRequest(Request.Method.PUT, mUrlAPI + action_Cart + "/" + cartItemId, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    CartJsonParser.parserCart(response);

                    getCartApiAll(userprofilesid, context);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    params.put("itemquantity", itemquantity + "");
                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }

    public void deleteCartItem(final int cartItemId, int userprofilesid, final Context context) {
        if (!HealthifyJsonParser.isConnectionInternet(context))
            Toast.makeText(context, "Sem Ligaçao a internet", Toast.LENGTH_SHORT).show();
        else {
            StringRequest req = new StringRequest(Request.Method.DELETE, mUrlAPI + action_Cart + "/" + cartItemId, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    getCartApiAll(userprofilesid, context);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    params.put("id", cartItemId + "");
                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }

    //endregion
    //region historico
    public void getHistorico(int userProfileId, final Context context) {
        if (!HealthifyJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Sem Ligaçao a internet", Toast.LENGTH_LONG).show();
            ArrayList<Refeicao> historico = getRefeicoesBD();
            if (refeicoesListener != null) {
                refeicoesListener.onRefreshHistorico(historico);
            }
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPI + action_GetHistorico + userProfileId, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    ArrayList<Refeicao> historico = HealthifyJsonParser.parserJsonRefeicao(response);

                    adicionarRefeicoesBD(historico);

                    if (refeicoesListener != null) {
                        refeicoesListener.onRefreshHistorico(historico);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }
    }

    //endregion
    //region Review
    public void getReviews(int userProfileId, final Context context) {
        if (!HealthifyJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Sem Ligaçao a internet", Toast.LENGTH_LONG).show();
            ArrayList<Review> reviews = getReviewsBD();
            if (reviewListener != null) {
                reviewListener.onRefreshReview(reviews);
            }
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPI + action_GetReview + userProfileId, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    ArrayList<Review> reviews = ReviewJsonParser.parserGetReview(response);
                    adicionarReviewsBD(reviews);

                    if (reviewListener != null) {
                        reviewListener.onRefreshReview(reviews);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "NÃO EXISTEM CRÍTICAS", Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }
    }

    public void addReview(String review, float rating, int fkIdRefeicao, int userprofilesid, final Context context) {
        if (!HealthifyJsonParser.isConnectionInternet(context))
            Toast.makeText(context, "Sem Ligaçao a internet", Toast.LENGTH_SHORT).show();
        else {
            StringRequest req = new StringRequest(Request.Method.POST, mUrlAPI + action_Review, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    System.out.println(error.getMessage());
                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    params.put("rating", rating + "");
                    params.put("review", review + "");
                    params.put("userprofilesid", userprofilesid + "");
                    params.put("mealsid", fkIdRefeicao + "");
                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }

    //endregion
    //region Cartoes Pagamento
    public void inserirCartaoPagamentoBG(CartaoPagamento cartaoPagamento, Context context) {

        Boolean confirmed = helper.adicionarMetodopagamento(cartaoPagamento);
        if (confirmed) {
            if (pagamentoListener != null) {
                pagamentoListener.afterCardAdd(confirmed);
            }
        } else {
            Toast.makeText(context, "Cartão não foi inserido com sucesso!", Toast.LENGTH_LONG).show();
        }
    }

    public void getCartoesPagamentoBD(Context context) {
        cartaoPagamentoArrayList = helper.carregarCartaoPagamento();
        if (cartaoPagamentoArrayList == null) {
            Toast.makeText(context, "Sem cartões inseridos!", Toast.LENGTH_SHORT).show();

        } else {
            if (pagamentoListener != null) {
                pagamentoListener.onCardToView(cartaoPagamentoArrayList);
            }
        }
    }

    public void payCart(int profileId,String Ncartao,Context context) {
        if (!HealthifyJsonParser.isConnectionInternet(context))
            Toast.makeText(context, "Sem Ligaçao a internet", Toast.LENGTH_SHORT).show();
        else {
            StringRequest req = new StringRequest(Request.Method.POST, mUrlAPI + action_Payment+profileId+pay+Ncartao, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    boolean message = true;

                    if (cartPayListener != null) {
                        cartPayListener.onCartPay(message,context);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    System.out.println(error.getMessage());
                }
            });
            volleyQueue.add(req);
        }
    }
    public void payCash(int profileId,Context context) {
        if (!HealthifyJsonParser.isConnectionInternet(context))
            Toast.makeText(context, "Sem Ligaçao a internet", Toast.LENGTH_SHORT).show();
        else {
            StringRequest req = new StringRequest(Request.Method.POST, mUrlAPI + action_Payment+cash+profileId, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    boolean message = true;

                    if (cartPayListener != null) {
                        cartPayListener.onCartPay(message,context);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    System.out.println(error.getMessage());
                }
            });
            volleyQueue.add(req);
        }
    }

    //endregion
    //region Operações de Staff
    public void setRegistarPonto(int profileId, Context context) {
        if (!HealthifyJsonParser.isConnectionInternet(context))
            Toast.makeText(context, "Sem Ligaçao a internet", Toast.LENGTH_SHORT).show();
        else {
            StringRequest req = new StringRequest(Request.Method.POST, mUrlAPI + action_workedtime + profileId, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    boolean message = StaffOperationsParser.WorkedTime(response);

                    if (relogioPontoListener != null) {
                        relogioPontoListener.onRefreshPonto(message);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    System.out.println(error.getMessage());
                }
            });
            volleyQueue.add(req);
        }
    }
    //endregion
    //region Operações de mesas

    public void ocuparMesa(String mesa, final Context context) {
        if (!HealthifyJsonParser.isConnectionInternet(context))
            Toast.makeText(context, "Sem Ligaçao a internet", Toast.LENGTH_SHORT).show();
        else {
            StringRequest req = new StringRequest(Request.Method.POST, mUrlAPI + action_ocuparmesa + mesa, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    TableReserveAPI reserve = TableReserveParser.parserGetReserve(response);

                    if (tableListener != null) {
                        tableListener.onSeatedClient(reserve,mesa);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    System.out.println(error.getMessage());
                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    params.put("tableid", mesa + "");
                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }

    public void reservarMesa(String mesa, final Context context) {
        if (!HealthifyJsonParser.isConnectionInternet(context))
            Toast.makeText(context, "Sem Ligaçao a internet", Toast.LENGTH_SHORT).show();
        else {
            StringRequest req = new StringRequest(Request.Method.POST, mUrlAPI + action_reservarmesa + mesa, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    TableReserveAPI reserve = TableReserveParser.parserGetReserve(response);

                    if (tableListener != null) {
                        tableListener.onTableReserve(reserve,mesa);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    System.out.println(error.getMessage());
                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    params.put("tableid", mesa + "");
                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }

    public void libertarMesa(String mesa, final Context context) {
        if (!HealthifyJsonParser.isConnectionInternet(context))
            Toast.makeText(context, "Sem Ligaçao a internet", Toast.LENGTH_SHORT).show();
        else {
            StringRequest req = new StringRequest(Request.Method.POST, mUrlAPI + action_libertarmesa + mesa, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    TableReserveAPI reserve = TableReserveParser.parserGetReserve(response);

                    if (tableListener != null) {
                        tableListener.onTableFree(reserve,mesa);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    System.out.println(error.getMessage());
                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    params.put("tableid", mesa + "");
                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }

    //endregion
}
