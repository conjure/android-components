package uk.co.conjure.components.auth

import io.reactivex.rxjava3.core.Single
import uk.co.conjure.components.auth.user.UserInfo

interface AuthInteractor {

    sealed class SignInResult {
        class Success(val userInfo: UserInfo) : SignInResult()
        class Failure(val error: SignInError) : SignInResult()
    }

    enum class SignInError {
        ERROR_USER_NOT_FOUND,
        ERROR_INVALID_CREDENTIALS,
        ERROR
    }

    sealed class SignUpResult {
        class Success(val userInfo: UserInfo) : SignUpResult()
        class Failure(val error: SignUpError) : SignUpResult()
    }

    enum class SignUpError {
        ERROR_USER_ALREADY_PRESENT,
        ERROR_INVALID_EMAIL,
        ERROR_INVALID_PASSWORD,
        ERROR
    }

    sealed class RequestPasswordResetResult {
        class Success(val resetToken: String?) : RequestPasswordResetResult()
        class Failure(val error: RequestPasswordResetError) : RequestPasswordResetResult()
    }

    enum class RequestPasswordResetError {
        ERROR_EMAIL,
        ERROR
    }

    /**
     * Immediately return a boolean indicating whether or not the given email is
     * syntactically valid. e.g. does the email have the correct form
     */
    fun isValidEmail(email: String): Boolean

    /**
     * Immediately return a boolean indicating whether or not the password is
     * syntactically valid. e.g. is the password long enough
     */
    fun isValidPassword(password: String): Boolean

    fun signIn(email: String, password: String): Single<SignInResult>

    fun signUp(email: String, password: String): Single<SignUpResult>

    fun requestPasswordReset(email: String): Single<RequestPasswordResetResult>
}